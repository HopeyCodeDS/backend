package be.kdg.prog6.landsideContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.landsideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.events.AppointmentScheduledEvent;
import be.kdg.prog6.landsideContext.ports.out.AppointmentScheduledPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppointmentScheduledAMQPPublisher implements AppointmentScheduledPort {

    public static final Logger log = LoggerFactory.getLogger(AppointmentScheduledAMQPPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public AppointmentScheduledAMQPPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void appointmentScheduled(Appointment appointment) {
        log.info("Publishing event that new appointment is scheduled");
        
        var eventHeader = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.APPOINTMENT_SCHEDULED)
                .build();
        
        var eventBody = new AppointmentScheduledEvent(
            appointment.getAppointmentId(),
            appointment.getSellerId(),
            appointment.getTruck().getLicensePlate().getValue(),
            appointment.getRawMaterial().getName(),
            appointment.getArrivalWindow().getStartTime(),
            appointment.getArrivalWindow().getEndTime(),
            appointment.getScheduledTime()
        );
        
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQModuleTopology.LANDSIDE_EVENTS_FAN_OUT, 
                "appointment.scheduled", 
                EventMessage.builder()
                    .eventHeader(eventHeader)
                    .eventBody(objectMapper.writeValueAsString(eventBody))
                    .build()
            );
        } catch (JsonProcessingException e) {
            log.error("Failed to publish appointment scheduled event", e);
        }
    }
} 