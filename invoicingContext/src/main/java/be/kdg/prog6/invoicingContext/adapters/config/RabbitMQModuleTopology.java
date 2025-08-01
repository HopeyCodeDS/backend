package be.kdg.prog6.invoicingContext.adapters.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("invoicingRabbitMQModuleTopology")
public class RabbitMQModuleTopology {

    public static final String EXCHANGE_NAME = "kdg.events";
    public static final String PURCHASE_ORDER_SUBMITTED_QUEUE = "purchase-order.submitted";
    public static final String PURCHASE_ORDER_SUBMITTED_ROUTING_KEY = "purchase-order.submitted";
    
    @Bean
    public TopicExchange kdgEventsExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }
    
    @Bean
    public Queue purchaseOrderSubmittedQueue() {
        return new Queue(PURCHASE_ORDER_SUBMITTED_QUEUE, true);
    }
    
    @Bean
    public Binding purchaseOrderSubmittedBinding() {
        return BindingBuilder.bind(purchaseOrderSubmittedQueue())
                .to(kdgEventsExchange())
                .with(PURCHASE_ORDER_SUBMITTED_ROUTING_KEY);
    }
    
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
} 