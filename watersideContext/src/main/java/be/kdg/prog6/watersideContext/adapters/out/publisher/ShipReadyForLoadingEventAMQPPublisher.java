package be.kdg.prog6.watersideContext.adapters.out.publisher;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.ShipReadyForLoadingEvent;
import be.kdg.prog6.watersideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.watersideContext.ports.out.ShipReadyForLoadingEventPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipReadyForLoadingEventAMQPPublisher implements ShipReadyForLoadingEventPublisherPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;  
    
    @Override
    public void publishShipReadyForLoadingEvent(ShipReadyForLoadingEvent event) {
        log.info("Publishing ship ready for loading event: {}", event.getShippingOrderId());
        
        // Create event message with header
        EventMessage eventMessage;
        try {
            eventMessage = EventMessage.builder()
                .eventHeader(EventHeader.builder()
                    .eventID(event.getShippingOrderId())
                    .eventCatalog(EventCatalog.LOADING_STARTED)
                    .build())
                .eventBody(objectMapper.writeValueAsString(event))
                .build();
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize ship ready for loading event: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to serialize ship ready for loading event", e);
        }
        
        rabbitTemplate.convertAndSend(RabbitMQModuleTopology.SHIP_READY_FOR_LOADING_FAN_OUT, "ship.ready.for.loading", eventMessage);
        
        log.info("Successfully published ship ready for loading event for shipping order: {}", 
            event.getShippingOrderId());
    }
} 