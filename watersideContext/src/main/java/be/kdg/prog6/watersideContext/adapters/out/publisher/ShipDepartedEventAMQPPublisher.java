package be.kdg.prog6.watersideContext.adapters.out.publisher;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.ShipDepartedEvent;
import be.kdg.prog6.watersideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.watersideContext.ports.out.ShipDepartedEventPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipDepartedEventAMQPPublisher implements ShipDepartedEventPublisherPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;  
    
    @Override
    public void publishShipDepartedEvent(ShipDepartedEvent event) {
        log.info("Publishing ship departed event: {}", event.shippingOrderId());
        
        // Create event message
        EventMessage eventMessage;
        try {
            eventMessage = EventMessage.builder()
                .eventHeader(EventHeader.builder()
                    .eventID(event.shippingOrderId())
                    .eventCatalog(EventCatalog.SHIP_DEPARTED)
                    .build())
                .eventBody(objectMapper.writeValueAsString(event))
                .build();
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize ship departed event: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to serialize ship departed event", e);
        }
        
        rabbitTemplate.convertAndSend(RabbitMQModuleTopology.SHIP_DEPARTED_FAN_OUT, "ship.departed", eventMessage);
        
        log.info("Successfully published ship departed event for shipping order to the invoicing context: {}", 
            event.shippingOrderId());
    }
} 