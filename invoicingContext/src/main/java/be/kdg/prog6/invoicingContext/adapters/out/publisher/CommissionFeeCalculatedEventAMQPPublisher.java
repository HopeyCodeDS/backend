package be.kdg.prog6.invoicingContext.adapters.out.publisher;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.CommissionFeeCalculatedEvent;
import be.kdg.prog6.invoicingContext.ports.out.CommissionFeeCalculatedEventPublisherPort;
import be.kdg.prog6.invoicingContext.adapters.config.RabbitMQModuleTopology;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommissionFeeCalculatedEventAMQPPublisher implements CommissionFeeCalculatedEventPublisherPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;  
    
    @Override
    public void publishCommissionFeeCalculatedEvent(CommissionFeeCalculatedEvent event) {
        log.info("Publishing commission fee calculated event: {}", event.commissionFeeId());
        
        EventMessage eventMessage;
        try {
            eventMessage = EventMessage.builder()
                .eventHeader(EventHeader.builder()
                    .eventID(event.commissionFeeId())
                    .eventCatalog(EventCatalog.COMMISSION_FEE_CALCULATED)
                    .build())
                .eventBody(objectMapper.writeValueAsString(event))
                .build();
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize commission fee calculated event: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to serialize commission fee calculated event", e);
        }
        
        rabbitTemplate.convertAndSend(RabbitMQModuleTopology.INVOICING_EVENTS_TOPIC, "commission.fee.calculated", eventMessage);
        
        log.info("Successfully published commission fee calculated event: ${} for PO: {}", 
            event.commissionAmount(), event.purchaseOrderNumber());
    }
} 