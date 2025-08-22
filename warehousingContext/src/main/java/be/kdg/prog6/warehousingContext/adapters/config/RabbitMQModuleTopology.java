package be.kdg.prog6.warehousingContext.adapters.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("warehousingRabbitMQModuleTopology")
public class RabbitMQModuleTopology {

    // Change from Fanout to Topic exchange
    public static final String WAREHOUSING_EVENTS_TOPIC = "warehousing-events";
    public static final String PURCHASE_ORDER_SUBMITTED_FAN_OUT = "purchase-order-submitted-events";
    public static final String WAREHOUSING_QUEUE = "warehousing-queue";
    
    // Direct exchange for warehouse activity events
    public static final String WAREHOUSE_ACTIVITY_DIRECT = "warehouse-activity-direct";
    public static final String WAREHOUSE_ACTIVITY_QUEUE = "warehouse-activity-queue";

    // new queue for PDT events
    public static final String PDT_GENERATED_QUEUE = "pdt-generated-queue";

    // warehouse assigned queue constant
    public static final String WAREHOUSE_ASSIGNED_QUEUE = "warehouse-assigned-queue";

    // PO matched with SO queue - listening to waterside context
    public static final String PO_MATCHED_WITH_SO_QUEUE = "po-matched-with-so-warehousing";

    // purchase order submitted queue constant
    public static final String PURCHASE_ORDER_SUBMITTED_QUEUE = "purchase-order.submitted.warehousing";
    public static final String PO_MATCHED_WITH_SO_FAN_OUT = "po-matched-with-so-fan-out";

    // Ship loaded event constants
    public static final String SHIP_LOADED_FAN_OUT = "ship.loaded";
    public static final String SHIP_LOADED_QUEUE = "ship.loaded.queue";

    @Bean
    TopicExchange warehousingEventsExchange() {
        return new TopicExchange(WAREHOUSING_EVENTS_TOPIC);
    }

    @Bean
    FanoutExchange purchaseOrderSubmittedFanOutExchange() {
        return new FanoutExchange(PURCHASE_ORDER_SUBMITTED_FAN_OUT);
    }

    @Bean
    Queue warehousingQueue() {
        return new Queue(WAREHOUSING_QUEUE);
    }

    @Bean
    DirectExchange warehouseActivityDirectExchange() {
        return new DirectExchange(WAREHOUSE_ACTIVITY_DIRECT);
    }

    @Bean
    Queue warehouseActivityQueue() {
        return new Queue(WAREHOUSE_ACTIVITY_QUEUE);
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
    Queue purchaseOrderSubmittedQueue() {
        return new Queue(PURCHASE_ORDER_SUBMITTED_QUEUE);
    }

    @Bean
    FanoutExchange poMatchedWithSoFanOutExchange() {
        return new FanoutExchange(PO_MATCHED_WITH_SO_FAN_OUT);
    }

    @Bean
    Queue poMatchedWithSoQueue() {
        return new Queue(PO_MATCHED_WITH_SO_QUEUE);
    }
    
    @Bean
    Binding warehouseAssignedBinding(TopicExchange warehousingEventsExchange, Queue warehouseAssignedQueue) {
        return BindingBuilder.bind(warehouseAssignedQueue).to(warehousingEventsExchange).with("warehouse.assigned");
    }

    @Bean
    Binding pdtGeneratedBinding(TopicExchange warehousingEventsExchange, Queue pdtGeneratedQueue) {
        return BindingBuilder.bind(pdtGeneratedQueue).to(warehousingEventsExchange).with("pdt.generated");
    }

    @Bean
    Binding purchaseOrderSubmittedBinding(FanoutExchange purchaseOrderSubmittedFanOutExchange, Queue purchaseOrderSubmittedQueue) {
        return BindingBuilder.bind(purchaseOrderSubmittedQueue).to(purchaseOrderSubmittedFanOutExchange);
    }

    @Bean
    Binding poMatchedWithSoBinding(FanoutExchange poMatchedWithSoFanOutExchange, Queue poMatchedWithSoQueue) {
        return BindingBuilder.bind(poMatchedWithSoQueue)
                .to(poMatchedWithSoFanOutExchange);
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

    @Bean
    Binding warehouseActivityBinding(DirectExchange warehouseActivityDirectExchange, Queue warehouseActivityQueue) {
        return BindingBuilder.bind(warehouseActivityQueue)
                .to(warehouseActivityDirectExchange)
                .with("warehouse.activity");
    }
} 