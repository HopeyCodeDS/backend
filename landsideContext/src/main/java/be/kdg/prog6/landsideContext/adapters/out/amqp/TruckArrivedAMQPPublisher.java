package be.kdg.prog6.landsideContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.landsideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.events.TruckArrivedEvent;
import be.kdg.prog6.landsideContext.ports.out.TruckArrivedPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate; 
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TruckArrivedAMQPPublisher implements TruckArrivedPort {

    public static final Logger log = LoggerFactory.getLogger(TruckArrivedAMQPPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public TruckArrivedAMQPPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void truckArrived(Appointment appointment) {
        log.info("Publishing event that truck has arrived");
        
        var eventHeader = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.TRUCK_ARRIVED)
                .build();
        
        var eventBody = new TruckArrivedEvent(
            appointment.getAppointmentId(),
            appointment.getTruck().getLicensePlate().getValue(),
            appointment.getArrivalWindow().getStartTime()
        );
        
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQModuleTopology.LANDSIDE_EVENTS_FAN_OUT, 
                "truck.arrived", 
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