package be.kdg.prog6.warehousingContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.PDTGenerated;
import be.kdg.prog6.warehousingContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.out.PDTGeneratedPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class PDTGeneratedAMQPPublisher implements PDTGeneratedPort {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public PDTGeneratedAMQPPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void pdtGenerated(PayloadDeliveryTicket pdt) {
        log.info("Publishing PDT generated event for truck: {}", pdt.getLicensePlate());
        
        var eventHeader = EventHeader.builder()
            .eventID(UUID.randomUUID())
            .eventCatalog(EventCatalog.PDT_GENERATED)
            .build();
            
        var eventBody = new PDTGenerated(
            pdt.getPdtId(),
            pdt.getLicensePlate(),
            pdt.getRawMaterialName(),
            pdt.getWarehouseNumber(),
            pdt.getConveyorBeltNumber(),
            pdt.getPayloadWeight(),
            pdt.getSellerId(),
            pdt.getDeliveryTime(),
            pdt.getNewWeighingBridgeNumber(),
            null
        );
        
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQModuleTopology.WAREHOUSING_EVENTS_FAN_OUT,
                "pdt.generated",
                EventMessage.builder()
                    .eventHeader(eventHeader)
                    .eventBody(objectMapper.writeValueAsString(eventBody))
                    .build()
                    );
                    log.info("Successfully published PDT generated event");
                } catch (JsonProcessingException e) {
                    log.error("Failed to serialize PDT event: {}", e.getMessage());
                    throw new RuntimeException("Failed to serialize PDT event", e);
                } catch (Exception e) {
                    log.error("Failed to publish PDT event: {}", e.getMessage());
                    throw new RuntimeException("Failed to publish PDT event", e);
                }
            }
} 