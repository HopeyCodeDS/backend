package be.kdg.prog6.warehousingContext.adapters.out.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.WarehouseAssigned;
import be.kdg.prog6.warehousingContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseAssignedPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class WarehouseAssignedAMQPPublisher implements WarehouseAssignedPort {

    public static final Logger log = LoggerFactory.getLogger(WarehouseAssignedAMQPPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public WarehouseAssignedAMQPPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void warehouseAssigned(WarehouseAssigned assignment) {
        log.info("Publishing event that warehouse is assigned");
        
        var eventHeader = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.WAREHOUSE_ASSIGNED)
                .build();
        
        var eventBody = new WarehouseAssigned(
            assignment.assignmentId(),
            assignment.licensePlate(),
            assignment.warehouseNumber(),
            assignment.rawMaterialName(),
            assignment.sellerId(),
            assignment.truckWeight()
        );
        
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQModuleTopology.WAREHOUSING_EVENTS_FAN_OUT, 
                "warehouse.assigned", 
                EventMessage.builder()
                    .eventHeader(eventHeader)
                    .eventBody(objectMapper.writeValueAsString(eventBody))
                    .build()
            );
        } catch (JsonProcessingException e) {
            log.error("Failed to publish warehouse assigned event", e);
        }
    }
} 