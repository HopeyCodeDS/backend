package be.kdg.prog6.warehousingContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.ShipLoadedEvent;
import be.kdg.prog6.warehousingContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.warehousingContext.ports.out.ShipLoadedEventPublisherPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipLoadedEventAMQPPublisher implements ShipLoadedEventPublisherPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    @Override
    public void publishShipLoadedEvent(ShipLoadedEvent event) {
        log.info("Publishing ship loaded event: {}", event.shippingOrderId());
        
        // Create event message
        EventMessage eventMessage;
        try {
            eventMessage = EventMessage.builder()
                .eventHeader(EventHeader.builder()
                    .eventID(UUID.randomUUID())
                    .eventCatalog(EventCatalog.SHIP_LOADED)
                    .build())
                .eventBody(objectMapper.writeValueAsString(event))
                .build();
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize ship loaded event: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to serialize ship loaded event", e);
        }
        
        rabbitTemplate.convertAndSend(RabbitMQModuleTopology.SHIP_LOADED_FAN_OUT, "ship.loaded", eventMessage);
        
        log.info("Successfully published ship loaded event for shipping order: {}", 
            event.shippingOrderId());
    }
} 