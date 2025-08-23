package be.kdg.prog6.landsideContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.WarehouseAssigned;
import be.kdg.prog6.landsideContext.ports.in.AssignWarehouseToTruckUseCase;
import be.kdg.prog6.landsideContext.ports.in.RegisterWeightAndExitBridgeUseCase;
import be.kdg.prog6.landsideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.landsideContext.domain.commands.RegisterWeightAndExitBridgeCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarehouseAssignedAMQPListener {
    
    private final AssignWarehouseToTruckUseCase assignWarehouseToTruckUseCase;
    private final ObjectMapper objectMapper;
    
    @RabbitListener(queues = RabbitMQModuleTopology.WAREHOUSE_ASSIGNED_QUEUE)
    public void handleWarehouseAssigned(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if (eventMessage.getEventHeader().getEventType() != EventCatalog.WAREHOUSE_ASSIGNED) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return; 
            }

            log.info("Processing WAREHOUSE_ASSIGNED event: {}", eventMessage.getEventHeader().getEventID());

            // Parse the event body
            WarehouseAssigned event = objectMapper.readValue(
                eventMessage.getEventBody(), 
                WarehouseAssigned.class
            );
            
            log.info("Received warehouse assigned event for license plate: {}", event.licensePlate());
            
            // Update truck with warehouse assignment using existing use case
            assignWarehouseToTruckUseCase.assignWarehouseToTruck(
                    event.licensePlate(),
                    event.warehouseNumber()
            );

            log.info("Successfully assigned truck {} to warehouse {}", event.licensePlate(), event.warehouseNumber());
            
        } catch (Exception e) {
            log.error("Error processing warehouse assigned event", e);
        }
    }
} 