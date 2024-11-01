package be.kdg.prog6.landsideContext.adapters.out;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableRabbit
public class RabbitMQConfig {

    // Exchange and Queue names for Landside context events
    public static final String PAYLOAD_EXCHANGE_NAME = "payload.exchange";
    public static final String DELIVERY_INITIATED_QUEUE_NAME = "deliveryInitiatedQueue";
    public static final String DELIVERY_COMPLETED_QUEUE_NAME = "deliveryCompletedQueue";
    public static final String CONVEYOR_BELT_ASSIGNED_QUEUE_NAME = "conveyorBeltAssignedQueue";  // New queue for conveyor belt assignments

    // Routing keys

    public static final String WAREHOUSE_EXCHANGE = "warehouse.exchange";
    public static final String WAREHOUSE_LOAD_QUEUE = "warehouseLoadQueue";
    public static final String WAREHOUSE_LOAD_ROUTING_KEY = "warehouse.load.status";

    // Define the topic exchange for payload-related events
    @Bean
    public TopicExchange payloadExchange() {
        return new TopicExchange(PAYLOAD_EXCHANGE_NAME);
    }

    @Bean
    public TopicExchange warehouseExchange() {
        return new TopicExchange(WAREHOUSE_EXCHANGE);
    }

    // Queue for initiated delivery events (Landside -> Warehousing Context)
    @Bean
    public Queue deliveryInitiatedQueue() {
        return new Queue(DELIVERY_INITIATED_QUEUE_NAME);
    }

    @Bean
    public Queue warehouseLoadQueue() {
        return new Queue(WAREHOUSE_LOAD_QUEUE);
    }

    // Queue for completed delivery events (Warehousing Context response)
    @Bean
    public Queue deliveryCompletedQueue() {
        return new Queue(DELIVERY_COMPLETED_QUEUE_NAME);
    }

    // New Queue for ConveyorBeltAssignedEvent (Warehousing Context -> Landside Context)
    @Bean
    public Queue conveyorBeltAssignedQueue() {
        return new Queue(CONVEYOR_BELT_ASSIGNED_QUEUE_NAME);
    }

    // Bind the initiated delivery queue to the payload exchange with a routing key
    @Bean
    public Binding initiatedBinding(Queue deliveryInitiatedQueue, TopicExchange payloadExchange) {
        return BindingBuilder.bind(deliveryInitiatedQueue)
                .to(payloadExchange)
                .with("payload.delivery.initiated");
    }

    // Bind the completed delivery queue to the payload exchange with a routing key
    @Bean
    public Binding completedBinding(Queue deliveryCompletedQueue, TopicExchange payloadExchange) {
        return BindingBuilder.bind(deliveryCompletedQueue)
                .to(payloadExchange)
                .with("payload.delivery.completed");
    }

    // Bind the conveyorBeltAssignedQueue to handle conveyor belt assignment events
    @Bean
    public Binding conveyorBeltAssignedBinding(Queue conveyorBeltAssignedQueue, TopicExchange payloadExchange) {
        return BindingBuilder.bind(conveyorBeltAssignedQueue)
                .to(payloadExchange)
                .with("payload.conveyorBelt.assigned");
    }

    @Bean
    public Binding warehouseLoadBinding(Queue warehouseLoadQueue, TopicExchange warehouseExchange) {
        return BindingBuilder.bind(warehouseLoadQueue).to(warehouseExchange).with(WAREHOUSE_LOAD_ROUTING_KEY);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Add this line to ensure proper handling of enum values
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        return mapper;
    }

    //    @Bean
//    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
//        return new Jackson2JsonMessageConverter(objectMapper);
//    }
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        converter.setCreateMessageIds(true);
        return converter;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}
