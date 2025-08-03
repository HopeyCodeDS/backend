package be.kdg.prog6.invoicingContext.adapters.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("invoicingRabbitMQModuleTopology")
public class RabbitMQModuleTopology {

    public static final String PURCHASE_ORDER_SUBMITTED_FAN_OUT = "purchase-order-submitted-events";
    public static final String PURCHASE_ORDER_SUBMITTED_LANDSIDE_QUEUE = "purchase-order.submitted.landside";
    public static final String PURCHASE_ORDER_SUBMITTED_WAREHOUSING_QUEUE = "purchase-order.submitted.warehousing";
    public static final String PURCHASE_ORDER_SUBMITTED_WATERSIDE_QUEUE = "purchase-order.submitted.waterside";
    
    @Bean
    FanoutExchange purchaseOrderSubmittedFanOutExchange() {
        return new FanoutExchange(PURCHASE_ORDER_SUBMITTED_FAN_OUT);
    }
    
    @Bean
    Queue purchaseOrderSubmittedLandsideQueue() {
        return new Queue(PURCHASE_ORDER_SUBMITTED_LANDSIDE_QUEUE);
    }
    
    @Bean
    Queue purchaseOrderSubmittedWarehousingQueue() {
        return new Queue(PURCHASE_ORDER_SUBMITTED_WAREHOUSING_QUEUE);
    }

    @Bean
    Queue purchaseOrderSubmittedWatersideQueue() {
        return new Queue(PURCHASE_ORDER_SUBMITTED_WATERSIDE_QUEUE);
    }
    
    @Bean
    Binding purchaseOrderSubmittedLandsideBinding(FanoutExchange purchaseOrderSubmittedFanOutExchange, Queue purchaseOrderSubmittedLandsideQueue) {
        return BindingBuilder.bind(purchaseOrderSubmittedLandsideQueue)
                .to(purchaseOrderSubmittedFanOutExchange);
    }
    
    @Bean
    Binding purchaseOrderSubmittedWarehousingBinding(FanoutExchange purchaseOrderSubmittedFanOutExchange, Queue purchaseOrderSubmittedWarehousingQueue) {
        return BindingBuilder.bind(purchaseOrderSubmittedWarehousingQueue)
                .to(purchaseOrderSubmittedFanOutExchange);
    }

    @Bean
    Binding purchaseOrderSubmittedWatersideBinding(FanoutExchange purchaseOrderSubmittedFanOutExchange, Queue purchaseOrderSubmittedWatersideQueue) {
        return BindingBuilder.bind(purchaseOrderSubmittedWatersideQueue)
                .to(purchaseOrderSubmittedFanOutExchange);
    }
} 