package be.kdg.prog6.warehousingContext.adapters.out.publisher;


import be.kdg.prog6.common.events.WarehouseCapacityUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class WarehousePublisher {
    private static final Logger log = LoggerFactory.getLogger(WarehousePublisher.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public WarehousePublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishWarehouseCapacityUpdate(String warehouseId, double currentLoad, double capacity) {
        boolean isOverCapacity = currentLoad > capacity;
        WarehouseCapacityUpdatedEvent event = new WarehouseCapacityUpdatedEvent(
                warehouseId,
                currentLoad,
                capacity,
                isOverCapacity,
                LocalDateTime.now()
        );

        try {
            String message = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend("warehouse.exchange", "warehouse.capacity.updated", message);
            log.info("Published WarehouseCapacityUpdatedEvent to warehouse.exchange: {}", message);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize WarehouseCapacityUpdatedEvent", e);
        }
    }
}
