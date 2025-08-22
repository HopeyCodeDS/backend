package be.kdg.prog6.warehousingContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.TruckLeftWeighingBridge;
import be.kdg.prog6.warehousingContext.domain.commands.AssignWarehouseCommand;
import be.kdg.prog6.warehousingContext.ports.in.AssignWarehouseUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TruckLeftWeighingBridgeAMQPListener {
    
    private final AssignWarehouseUseCase assignWarehouseUseCase;
    private final ObjectMapper objectMapper;
    
    @RabbitListener(queues = "landside-events-queue")
    public void handleTruckLeftWeighingBridge(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if (!EventCatalog.TRUCK_LEFT_WEIGHING_BRIDGE.equals(eventMessage.getEventHeader().getEventType())) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return;
            }
            // Parse the event body
            TruckLeftWeighingBridge event = objectMapper.readValue(
                eventMessage.getEventBody(), 
                TruckLeftWeighingBridge.class
            );
            
            log.info("Received truck left weighing bridge event for license plate: {}", event.licensePlate());
            
            // Create command for warehouse assignment
            AssignWarehouseCommand command = new AssignWarehouseCommand(
                event.licensePlate(),
                event.rawMaterialName(),
                event.sellerId(),
                event.truckWeight()
            );
            
            // Assign warehouse
            String warehouseNumber = assignWarehouseUseCase.assignWarehouse(command);
            
            log.info("Warehouse {} assigned to truck {}", warehouseNumber, event.licensePlate());
            
        } catch (Exception e) {
            log.error("Error processing truck left weighing bridge event", e);
        }
    }
} 