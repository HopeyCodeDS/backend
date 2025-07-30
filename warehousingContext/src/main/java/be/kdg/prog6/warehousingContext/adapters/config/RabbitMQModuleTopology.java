package be.kdg.prog6.warehousingContext.adapters.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("warehousingRabbitMQModuleTopology")
public class RabbitMQModuleTopology {

    public static final String WAREHOUSING_EVENTS_FAN_OUT = "warehousing-events";
    public static final String WAREHOUSING_QUEUE = "warehousing-queue";

    @Bean
    FanoutExchange warehousingEventsExchange() {
        return new FanoutExchange(WAREHOUSING_EVENTS_FAN_OUT);
    }

    @Bean
    Queue warehousingQueue() {
        return new Queue(WAREHOUSING_QUEUE);
    }

    @Bean
    Binding warehousingEventsBinding(FanoutExchange warehousingEventsExchange, Queue warehousingQueue) {
        return BindingBuilder.bind(warehousingQueue).to(warehousingEventsExchange);
    }
} 