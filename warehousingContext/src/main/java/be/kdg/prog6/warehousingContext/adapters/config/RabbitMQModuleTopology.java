package be.kdg.prog6.warehousingContext.adapters.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("warehousingRabbitMQModuleTopology")
public class RabbitMQModuleTopology {

    // Change from Fanout to Topic exchange
    public static final String WAREHOUSING_EVENTS_TOPIC = "warehousing-events";
    public static final String WAREHOUSING_QUEUE = "warehousing-queue";
    
    // new queue for PDT events
    public static final String PDT_GENERATED_QUEUE = "pdt-generated-queue";

    // warehouse assigned queue constant
    public static final String WAREHOUSE_ASSIGNED_QUEUE = "warehouse-assigned-queue";

    @Bean
    TopicExchange warehousingEventsExchange() {
        return new TopicExchange(WAREHOUSING_EVENTS_TOPIC);
    }

    @Bean
    Queue warehousingQueue() {
        return new Queue(WAREHOUSING_QUEUE);
    }

    @Bean
    Queue pdtGeneratedQueue() {
        return new Queue(PDT_GENERATED_QUEUE);
    }

    @Bean
    Queue warehouseAssignedQueue() {
        return new Queue(WAREHOUSE_ASSIGNED_QUEUE);
    }

    @Bean
    Binding warehouseAssignedBinding(TopicExchange warehousingEventsExchange, Queue warehouseAssignedQueue) {
        return BindingBuilder.bind(warehouseAssignedQueue).to(warehousingEventsExchange).with("warehouse.assigned");
    }

    @Bean
    Binding pdtGeneratedBinding(TopicExchange warehousingEventsExchange, Queue pdtGeneratedQueue) {
        return BindingBuilder.bind(pdtGeneratedQueue).to(warehousingEventsExchange).with("pdt.generated");
    }
} 