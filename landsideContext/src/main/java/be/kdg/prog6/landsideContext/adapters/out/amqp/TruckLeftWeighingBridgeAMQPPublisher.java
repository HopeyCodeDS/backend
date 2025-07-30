package be.kdg.prog6.landsideContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.TruckLeftWeighingBridge;
import be.kdg.prog6.landsideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.ports.out.TruckLeftWeighingBridgePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TruckLeftWeighingBridgeAMQPPublisher implements TruckLeftWeighingBridgePort {

    public static final Logger log = LoggerFactory.getLogger(TruckLeftWeighingBridgeAMQPPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public TruckLeftWeighingBridgeAMQPPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void truckLeftWeighingBridge(TruckMovement movement, String rawMaterialName, String sellerId) {
        log.info("Publishing event that truck left weighing bridge");
        
        var eventHeader = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.TRUCK_LEFT_WEIGHING_BRIDGE)
                .build();
        
        var eventBody = new TruckLeftWeighingBridge(
            movement.getMovementId(),
            movement.getLicensePlate().getValue(),
            movement.getAssignedBridgeNumber(),
            rawMaterialName,
            sellerId,
            movement.getTruckWeight()
        );
        
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQModuleTopology.LANDSIDE_EVENTS_FAN_OUT, 
                "truck.left.weighing.bridge", 
                EventMessage.builder()
                    .eventHeader(eventHeader)
                    .eventBody(objectMapper.writeValueAsString(eventBody))
                    .build()
            );
        } catch (JsonProcessingException e) {
            log.error("Failed to publish truck left weighing bridge event", e);
        }
    }
} 