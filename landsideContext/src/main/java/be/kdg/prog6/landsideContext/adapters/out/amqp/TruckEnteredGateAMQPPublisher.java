package be.kdg.prog6.landsideContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.landsideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.events.TruckEnteredGate;
import be.kdg.prog6.landsideContext.ports.out.TruckEnteredGatePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TruckEnteredGateAMQPPublisher implements TruckEnteredGatePort {

    public static final Logger log = LoggerFactory.getLogger(TruckEnteredGateAMQPPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public TruckEnteredGateAMQPPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void truckEnteredGate(TruckMovement truckMovement) {
        log.info("Publishing event that truck has entered gate");
        
        var eventHeader = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.TRUCK_ARRIVED)
                .build();
        
        var eventBody = new TruckEnteredGate(
            truckMovement.getMovementId(),
            truckMovement.getLicensePlate().getValue(),
            truckMovement.getEntryTime()
        );
        
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQModuleTopology.LANDSIDE_EVENTS_FAN_OUT, 
                "truck.entered.gate", 
                EventMessage.builder()
                    .eventHeader(eventHeader)
                    .eventBody(objectMapper.writeValueAsString(eventBody))
                    .build()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
} 