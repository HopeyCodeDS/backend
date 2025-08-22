package be.kdg.prog6.landsideContext.adapters.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("landsideRabbitMQModuleTopology")
public class RabbitMQModuleTopology {

    public static final String LANDSIDE_EVENTS_FAN_OUT = "landside-events";
    public static final String LANDSIDE_APPOINTMENT_COMMANDS = "landside-appointment-commands";
    public static final String LANDSIDE_TRUCK_COMMANDS = "landside-truck-commands";
    public static final String LANDSIDE_WEIGHING_BRIDGE_COMMANDS = "landside-weighing-bridge-commands";
    public static final String LANDSIDE_EXCHANGE = "landside.exchange";
    
    public static final String LANDSIDE_EVENTS_QUEUE = "landside-events-queue";
    public static final String LANDSIDE_APPOINTMENT_QUEUE = "landside-appointment-queue";
    public static final String LANDSIDE_TRUCK_QUEUE = "landside-truck-queue";
    public static final String LANDSIDE_WEIGHING_BRIDGE_QUEUE = "landside-weighing-bridge-queue";

    // queue constants for warehousing events
    public static final String WAREHOUSING_EVENTS_TOPIC = "warehousing-events";
    public static final String WAREHOUSE_ASSIGNED_QUEUE = "warehouse-assigned-queue";
    public static final String PDT_GENERATED_QUEUE = "pdt-generated-queue";

    // purchase order submitted queue constant
    public static final String PURCHASE_ORDER_SUBMITTED_QUEUE = "purchase-order.submitted";

    @Bean
    TopicExchange landsideExchange() {
        return new TopicExchange(LANDSIDE_EXCHANGE);
    }

    @Bean
    FanoutExchange landsideEventsExchange() {
        return new FanoutExchange(LANDSIDE_EVENTS_FAN_OUT);
    }

    @Bean
    Queue landsideEventsQueue() {
        return new Queue(LANDSIDE_EVENTS_QUEUE);
    }

    @Bean
    Queue warehouseAssignedQueue() {
        return new Queue(WAREHOUSE_ASSIGNED_QUEUE);
    }

    @Bean
    Queue pdtGeneratedQueue() {
        return new Queue(PDT_GENERATED_QUEUE);
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
    TopicExchange warehousingEventsExchange() {
        return new TopicExchange(WAREHOUSING_EVENTS_TOPIC);
    }

    @Bean
    Binding landsideEventsBinding(FanoutExchange landsideEventsExchange, Queue landsideEventsQueue) {
        return BindingBuilder.bind(landsideEventsQueue).to(landsideEventsExchange);
    }

    @Bean
    TopicExchange landsideAppointmentCommandExchange() {
        return new TopicExchange(LANDSIDE_APPOINTMENT_COMMANDS);
    }

    @Bean
    Queue landsideAppointmentCommandQueue() {
        return new Queue(LANDSIDE_APPOINTMENT_QUEUE);
    }

    @Bean
    Binding appointmentCommandBinding(TopicExchange landsideAppointmentCommandExchange, Queue landsideAppointmentCommandQueue) {
        return BindingBuilder.bind(landsideAppointmentCommandQueue).to(landsideAppointmentCommandExchange).with("appointment.command.#");
    }

    @Bean
    TopicExchange landsideTruckCommandExchange() {
        return new TopicExchange(LANDSIDE_TRUCK_COMMANDS);
    }

    @Bean
    Queue landsideTruckCommandQueue() {
        return new Queue(LANDSIDE_TRUCK_QUEUE);
    }

    @Bean
    Binding truckCommandBinding(TopicExchange landsideTruckCommandExchange, Queue landsideTruckCommandQueue) {
        return BindingBuilder.bind(landsideTruckCommandQueue).to(landsideTruckCommandExchange).with("truck.command.#");
    }

    @Bean
    TopicExchange landsideWeighingBridgeCommandExchange() {
        return new TopicExchange(LANDSIDE_WEIGHING_BRIDGE_COMMANDS);
    }

    @Bean
    Queue landsideWeighingBridgeCommandQueue() {
        return new Queue(LANDSIDE_WEIGHING_BRIDGE_QUEUE);
    }

    @Bean
    Binding weighingBridgeCommandBinding(TopicExchange landsideWeighingBridgeCommandExchange, Queue landsideWeighingBridgeCommandQueue) {
        return BindingBuilder.bind(landsideWeighingBridgeCommandQueue).to(landsideWeighingBridgeCommandExchange).with("weighing.bridge.command.#");
    }

    @Bean
    Queue purchaseOrderSubmittedQueue() {
        return new Queue(PURCHASE_ORDER_SUBMITTED_QUEUE);
    }

    @Bean
    Binding purchaseOrderSubmittedBinding(TopicExchange warehousingEventsExchange, Queue purchaseOrderSubmittedQueue) {
        return BindingBuilder.bind(purchaseOrderSubmittedQueue).to(warehousingEventsExchange).with("purchase-order.submitted");
    }

}
