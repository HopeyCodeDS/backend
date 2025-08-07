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

    // Listen to warehousing events
    public static final String WAREHOUSING_EVENTS_TOPIC = "warehousing-events";
    public static final String PDT_GENERATED_QUEUE = "invoicing.pdt-generated-events";
    public static final String SHIP_LOADED_QUEUE = "invoicing.ship-loaded-events";

    // Warehouse Activity Direct Exchange
    public static final String WAREHOUSE_ACTIVITY_DIRECT = "warehouse-activity-direct";
    public static final String WAREHOUSE_ACTIVITY_QUEUE = "warehouse-activity-queue";
 
    // Invoicing events
    public static final String INVOICING_EVENTS_TOPIC = "invoicing-events";
    public static final String COMMISSION_FEE_CALCULATED_QUEUE = "commission-fee-calculated-queue";
    public static final String STORAGE_FEE_CALCULATED_QUEUE = "storage-fee-calculated-queue";
    
    
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
    
    @Bean
    Queue commissionFeeCalculatedQueue() {
        return new Queue(COMMISSION_FEE_CALCULATED_QUEUE);
    }
    
    // @Bean
    // Binding commissionFeeCalculatedBinding(FanoutExchange invoicingEventsFanOutExchange, Queue commissionFeeCalculatedQueue) {
    //     return BindingBuilder.bind(commissionFeeCalculatedQueue)
    //             .to(invoicingEventsFanOutExchange);
    // }

    @Bean
    TopicExchange warehousingEventsExchange() {
        return new TopicExchange(WAREHOUSING_EVENTS_TOPIC);
    }

    @Bean
    TopicExchange invoicingEventsExchange() {
        return new TopicExchange(INVOICING_EVENTS_TOPIC);
    }

    @Bean
    Queue pdtGeneratedQueue() {
        return new Queue(PDT_GENERATED_QUEUE);
    }

    @Bean
    Queue shipLoadedQueue() {
        return new Queue(SHIP_LOADED_QUEUE);
    }

    @Bean
    Queue storageFeeCalculatedQueue() {
        return new Queue(STORAGE_FEE_CALCULATED_QUEUE);
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
    Binding warehouseActivityBinding(DirectExchange warehouseActivityDirectExchange, Queue warehouseActivityQueue) {
        return BindingBuilder.bind(warehouseActivityQueue)
            .to(warehouseActivityDirectExchange)
            .with("warehouse.activity");
    }

    // Bind to warehousing events (listen to PDT generated)
    @Bean
    Binding pdtGeneratedBinding(TopicExchange warehousingEventsExchange, Queue pdtGeneratedQueue) {
        return BindingBuilder.bind(pdtGeneratedQueue)
            .to(warehousingEventsExchange)
            .with("pdt.generated");
    }

    // Bind to ship loaded events (listen to ship loaded)
    @Bean
    Binding shipLoadedBinding(FanoutExchange shipLoadedFanOutExchange, Queue shipLoadedQueue) {
        return BindingBuilder.bind(shipLoadedQueue)
            .to(shipLoadedFanOutExchange);
    }

    // Bind invoicing events
    @Bean
    Binding commissionFeeCalculatedBinding(TopicExchange invoicingEventsExchange, Queue commissionFeeCalculatedQueue) {
        return BindingBuilder.bind(commissionFeeCalculatedQueue)
            .to(invoicingEventsExchange)
            .with("commission.fee.calculated");
    }

    @Bean
    Binding storageFeeCalculatedBinding(TopicExchange invoicingEventsExchange, Queue storageFeeCalculatedQueue) {
        return BindingBuilder.bind(storageFeeCalculatedQueue)
            .to(invoicingEventsExchange)
            .with("storage.fee.calculated");
    }
} 