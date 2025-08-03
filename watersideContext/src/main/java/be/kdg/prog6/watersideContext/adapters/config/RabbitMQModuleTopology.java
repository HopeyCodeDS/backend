package be.kdg.prog6.watersideContext.adapters.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("watersideRabbitMQModuleTopology")
public class RabbitMQModuleTopology {

    public static final String WATERSIDE_EVENTS_FAN_OUT = "waterside-events";
    
    public static final String WATERSIDE_EVENTS_QUEUE = "waterside-events-queue";

    // queue constants for warehousing events
    public static final String WAREHOUSING_EVENTS_TOPIC = "warehousing-events";
    public static final String WAREHOUSE_ASSIGNED_QUEUE = "warehouse-assigned-queue";

    // topic constants for waterside events
    public static final String WATERSIDE_EVENTS_TOPIC = "waterside-events-topic";

    // queue constants for waterside events
    public static final String SHIPPING_ORDER_SUBMITTED_QUEUE = "shipping-order.submitted";

    // purchase order submitted queue constant - listening to invoicing context
    public static final String PURCHASE_ORDER_SUBMITTED_QUEUE = "purchase-order.submitted.waterside";

    @Bean
    FanoutExchange watersideEventsExchange() {
        return new FanoutExchange(WATERSIDE_EVENTS_FAN_OUT);
    }

    @Bean
    TopicExchange watersideEventsTopicExchange() {
        return new TopicExchange(WATERSIDE_EVENTS_TOPIC);
    }

    @Bean
    Queue watersideEventsQueue() {
        return new Queue(WATERSIDE_EVENTS_QUEUE);
    }

    @Bean
    Queue warehouseAssignedQueue() {
        return new Queue(WAREHOUSE_ASSIGNED_QUEUE);
    }

    @Bean
    Queue purchaseOrderSubmittedQueue() {
        return new Queue(PURCHASE_ORDER_SUBMITTED_QUEUE);
    }

    @Bean
    public Queue shippingOrderSubmittedQueue() {
        return new Queue(SHIPPING_ORDER_SUBMITTED_QUEUE);
    }

    @Bean
    Binding warehouseAssignedBinding(TopicExchange warehousingEventsExchange, Queue warehouseAssignedQueue) {
        return BindingBuilder.bind(warehouseAssignedQueue).to(warehousingEventsExchange).with("warehouse.assigned");
    }

    @Bean
    TopicExchange warehousingEventsExchange() {
        return new TopicExchange(WAREHOUSING_EVENTS_TOPIC);
    }

    @Bean
    Binding watersideEventsBinding(FanoutExchange watersideEventsExchange, Queue watersideEventsQueue) {
        return BindingBuilder.bind(watersideEventsQueue).to(watersideEventsExchange);
    }

    @Bean
    Binding shippingOrderSubmittedBinding(FanoutExchange watersideEventsExchange, Queue shippingOrderSubmittedQueue) {
        return BindingBuilder.bind(shippingOrderSubmittedQueue)
                .to(watersideEventsExchange);
    }

    // This binding connects to the InvoicingContext's fanout exchange
    @Bean
    Binding purchaseOrderSubmittedBinding(FanoutExchange purchaseOrderSubmittedFanOutExchange, Queue purchaseOrderSubmittedQueue) {
        return BindingBuilder.bind(purchaseOrderSubmittedQueue)
                .to(purchaseOrderSubmittedFanOutExchange);
    }
}
