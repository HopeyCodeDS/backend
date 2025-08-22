package be.kdg.prog6.landsideContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.landsideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import be.kdg.prog6.landsideContext.ports.out.WeighbridgeTicketGeneratedPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeighbridgeTicketGeneratedAMQPPublisher implements WeighbridgeTicketGeneratedPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    @Override
    public void weighbridgeTicketGenerated(WeighbridgeTicket ticket) {
        try {
            EventHeader header = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.WEIGHBRIDGE_TICKET_GENERATED)
                .build();
            
            String eventBody = objectMapper.writeValueAsString(ticket);
            
            EventMessage eventMessage = EventMessage.builder()
                .eventHeader(header)
                .eventBody(eventBody)
                .build();
            
            String message = objectMapper.writeValueAsString(eventMessage);
            
            rabbitTemplate.convertAndSend(RabbitMQModuleTopology.LANDSIDE_EXCHANGE, "landside.weighbridge.ticket.generated", message);
            
            log.info("Published WeighbridgeTicketGenerated event for ticket: {}", ticket.getTicketId());
            
        } catch (Exception e) {
            log.error("Failed to publish WeighbridgeTicketGenerated event for ticket: {}", 
                ticket.getTicketId(), e);
        }
    }
} 