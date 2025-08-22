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

    // fanout constants for ship ready for loading events
    public static final String SHIP_READY_FOR_LOADING_FAN_OUT = "ship.ready.for.loading";
    public static final String SHIP_READY_FOR_LOADING_QUEUE = "ship.ready.for.loading.queue";

    public static final String PURCHASE_ORDER_MATCHED_WITH_SHIPPING_ORDER_FAN_OUT = "po-matched-with-so-fan-out";
    public static final String PURCHASE_ORDER_MATCHED_WITH_SHIPPING_ORDER_QUEUE = "po-matched-with-so-queue";

    public static final String SHIP_LOADED_FAN_OUT = "ship.loaded";
    public static final String SHIP_LOADED_QUEUE = "ship.loaded.queue";

    public static final String SHIP_DEPARTED_FAN_OUT = "ship.departed";
    public static final String SHIP_DEPARTED_QUEUE = "ship.departed.queue";

    @Bean
    FanoutExchange watersideEventsExchange() {
        return new FanoutExchange(WATERSIDE_EVENTS_FAN_OUT);
    }

    @Bean
    FanoutExchange shipReadyForLoadingFanOutExchange() {
        return new FanoutExchange(SHIP_READY_FOR_LOADING_FAN_OUT);
    }

    @Bean
    Queue shipReadyForLoadingQueue() {
        return new Queue(SHIP_READY_FOR_LOADING_QUEUE);
    }

    @Bean
    FanoutExchange purchaseOrderMatchedWithShippingOrderFanOutExchange() {
        return new FanoutExchange(PURCHASE_ORDER_MATCHED_WITH_SHIPPING_ORDER_FAN_OUT);
    }

    @Bean
    Queue purchaseOrderMatchedWithShippingOrderQueue() {
        return new Queue(PURCHASE_ORDER_MATCHED_WITH_SHIPPING_ORDER_QUEUE);
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

    @Bean
    Binding shipReadyForLoadingBinding(FanoutExchange shipReadyForLoadingFanOutExchange, Queue shipReadyForLoadingQueue) {
        return BindingBuilder.bind(shipReadyForLoadingQueue)
                .to(shipReadyForLoadingFanOutExchange);
    }

    @Bean
    Binding purchaseOrderMatchedWithShippingOrderBinding(FanoutExchange purchaseOrderMatchedWithShippingOrderFanOutExchange, Queue purchaseOrderMatchedWithShippingOrderQueue) {
        return BindingBuilder.bind(purchaseOrderMatchedWithShippingOrderQueue)
                .to(purchaseOrderMatchedWithShippingOrderFanOutExchange);
    }

    @Bean
    FanoutExchange shipDepartedFanOutExchange() {
        return new FanoutExchange(SHIP_DEPARTED_FAN_OUT);
    }

    @Bean
    Queue shipDepartedQueue() {
        return new Queue(SHIP_DEPARTED_QUEUE);
    }

    @Bean
    Binding shipDepartedBinding(FanoutExchange shipDepartedFanOutExchange, Queue shipDepartedQueue) {
        return BindingBuilder.bind(shipDepartedQueue)
                .to(shipDepartedFanOutExchange);
    }

    @Bean
    FanoutExchange shipLoadedFanOutExchange() {
        return new FanoutExchange(SHIP_LOADED_FAN_OUT);
    }

    @Bean
    Queue shipLoadedQueue() {
        return new Queue(SHIP_LOADED_QUEUE);
    }

    @Bean
    Binding shipLoadedBinding(FanoutExchange shipLoadedFanOutExchange, Queue shipLoadedQueue) {
        return BindingBuilder.bind(shipLoadedQueue)
                .to(shipLoadedFanOutExchange);
    }
}
