package be.kdg.prog6.warehousingContext.adapters.out.publisher;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.WarehouseActivityEvent;
import be.kdg.prog6.warehousingContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseActivityEventPublisherPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarehouseActivityEventAMQPPublisher implements WarehouseActivityEventPublisherPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    @Override
    public void publishWarehouseActivityEvent(WarehouseActivityEvent event) {
        log.info("Publishing warehouse activity event: {}", event.vesselNumber());
        
        // Create event message
        EventMessage eventMessage;
        try {
            eventMessage = EventMessage.builder()
                .eventHeader(EventHeader.builder()
                    .eventID(UUID.randomUUID())
                    .eventCatalog(EventCatalog.WAREHOUSE_ACTIVITY)
                    .build())
                .eventBody(objectMapper.writeValueAsString(event))
                .build();
            
            rabbitTemplate.convertAndSend(RabbitMQModuleTopology.WAREHOUSE_ACTIVITY_DIRECT, "warehouse.activity", eventMessage);
            
            log.info("Published warehouse activity event: {} for warehouse: {} with action: {} and {} tons of {}", 
                event.activityId(), event.warehouseNumber(), event.action(), event.amount(), event.materialType());
                
        } catch (Exception e) {
            log.error("Error publishing warehouse activity event: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to publish warehouse activity event", e);
        }
    }
} 