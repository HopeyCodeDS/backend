package be.kdg.prog6.landsideContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.WarehouseAssigned;
import be.kdg.prog6.landsideContext.core.RegisterWeightAndExitBridgeUseCaseImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarehouseAssignedAMQPListener {
    
    private final RegisterWeightAndExitBridgeUseCaseImpl registerWeightAndExitBridgeUseCase;
    private final ObjectMapper objectMapper;
    
    @RabbitListener(queues = "warehousing-queue")
    public void handleWarehouseAssigned(EventMessage eventMessage) {
        try {
            // Parse the event body
            WarehouseAssigned event = objectMapper.readValue(
                eventMessage.getEventBody(), 
                WarehouseAssigned.class
            );
            
            log.info("Received warehouse assigned event for license plate: {}", event.licensePlate());
            
            // Update truck with warehouse assignment using existing use case
            registerWeightAndExitBridgeUseCase.assignWarehouseToTruck(
                event.licensePlate(), 
                event.warehouseNumber()
            );
            
            log.info("Truck {} assigned to warehouse {}", event.licensePlate(), event.warehouseNumber());
            
        } catch (Exception e) {
            log.error("Error processing warehouse assigned event", e);
        }
    }
} 