package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.common.events.WeighBridgeTicketGeneratedEvent;
import be.kdg.prog6.common.events.WeighingBridgeAssignedEvent;
import be.kdg.prog6.landsideContext.domain.events.WarehouseAssignedEvent;
import be.kdg.prog6.landsideContext.ports.out.DomainEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisherAdapter implements DomainEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(DomainEventPublisherAdapter.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public DomainEventPublisherAdapter(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(WeighingBridgeAssignedEvent event) {
        log.info("Publishing WeighingBridgeAssignedEvent for license plate: {}", event.licensePlate());
        sendEvent("payload.exchange", "payload.weighingBridge.assigned", event);
    }

    @Override
    public void publish(WarehouseAssignedEvent event) {
        log.info("Publishing WarehouseAssignedEvent for license plate: {}", event.licensePlate());
        sendEvent("payload.exchange", "payload.warehouse.assigned", event);
    }

    @Override
    public void publish(WeighBridgeTicketGeneratedEvent event) {
        log.info("Publishing WeighBridgeTicketGeneratedEvent for truck with license plate: {}", event.getLicensePlate());
        sendEvent("payload.exchange", "payload.weighbridge.ticket.generated", event);
    }

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
