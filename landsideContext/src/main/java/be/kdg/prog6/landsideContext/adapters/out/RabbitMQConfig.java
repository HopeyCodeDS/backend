package be.kdg.prog6.landsideContext.adapters.out;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "dockExchange";
    public static final String QUEUE_NAME = "dockQueue";
    public static final String ROUTING_KEY = "dock.routingKey";

    @Bean
    public TopicExchange dockExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue dockQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding(Queue dockQueue, TopicExchange dockExchange) {
        return BindingBuilder
                .bind(dockQueue)
                .to(dockExchange)
                .with(ROUTING_KEY);
    }
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
