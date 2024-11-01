package be.kdg.prog6.warehousingContext.adapters.out;

import be.kdg.prog6.common.events.ConveyorBeltAssignedEvent;
import be.kdg.prog6.common.events.PayloadReceivedEvent;
import be.kdg.prog6.common.events.WarehouseLoadStatusEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private static final String PAYLOAD_EXCHANGE = "payload.exchange";
    private static final String WAREHOUSE_EXCHANGE = "warehouse.exchange";

    @Autowired
    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    // Publish event for assigning a conveyor belt
    public void publishConveyorBeltAssignedEvent(ConveyorBeltAssignedEvent event) {
        log.info("Publishing ConveyorBeltAssignedEvent for license plate: {}", event.getLicensePlate());
        sendEvent(PAYLOAD_EXCHANGE, "payload.conveyorBelt.assigned", event);
    }

    // Publish event for completed payload delivery
    public void publishPayloadDeliveredEvent(PayloadReceivedEvent event) {
        log.info("Publishing PayloadReceivedEvent for license plate: {}", event.getLicensePlate());
        sendEvent(PAYLOAD_EXCHANGE, "payload.delivery.completed", event);
    }

    // New method to publish warehouse load status updates
    public void publishWarehouseLoadStatusEvent(WarehouseLoadStatusEvent event) {
        log.info("Publishing WarehouseLoadStatusEvent for warehouse: {}, current load: {}",
                event.getWarehouseId(), event.getCurrentLoadPercentage());
        sendEvent(WAREHOUSE_EXCHANGE, "warehouse.load.status", event);
    }

    // Helper method to serialize and send any event type
    private <T> void sendEvent(String exchange, String routingKey, T event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info("Successfully published event to {} with routing key {}: {}", exchange, routingKey, message);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event {} for routing key {}", event, routingKey, e);
        }
    }
}
