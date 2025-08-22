package be.kdg.prog6.landsideContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.landsideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.events.WeighingBridgeAssigned;
import be.kdg.prog6.landsideContext.ports.out.WeighingBridgeAssignedPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class WeighingBridgeAssignedAMQPPublisher implements WeighingBridgeAssignedPort {

    public static final Logger log = LoggerFactory.getLogger(WeighingBridgeAssignedAMQPPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public WeighingBridgeAssignedAMQPPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void weighingBridgeAssigned(TruckMovement truckMovement) {
        log.info("Publishing event that weighing bridge has been assigned");
        
        var eventHeader = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.WEIGHING_BRIDGE_ASSIGNED)
                .build();
        
        var eventBody = new WeighingBridgeAssigned(
            truckMovement.getMovementId(),
            truckMovement.getLicensePlate().getValue(),
            truckMovement.getAssignedBridgeNumber(),
            LocalDateTime.now() // Assignment time
        );
        
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQModuleTopology.LANDSIDE_EVENTS_FAN_OUT, 
                "weighing.bridge.assigned", 
                EventMessage.builder()
                    .eventHeader(eventHeader)
                    .eventBody(objectMapper.writeValueAsString(eventBody))
                    .build()
            );

            log.info("Successfully published weighing bridge assigned event");

        } catch (JsonProcessingException e) {
            log.error("Failed to publish weighing bridge assigned event", e);
            // throw new RuntimeException(e);
        }
    }
} 