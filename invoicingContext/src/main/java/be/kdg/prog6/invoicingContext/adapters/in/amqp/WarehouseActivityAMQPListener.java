package be.kdg.prog6.invoicingContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.WarehouseActivityEvent;
import be.kdg.prog6.invoicingContext.domain.StorageTracking;
import be.kdg.prog6.invoicingContext.ports.out.StorageTrackingRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarehouseActivityAMQPListener {
    
    private final StorageTrackingRepositoryPort storageTrackingRepositoryPort;
    private final ObjectMapper objectMapper;
    
    @RabbitListener(queues = "warehouse-activity-queue")
    public void handleWarehouseActivity(EventMessage eventMessage) {
        try {
            if (!EventCatalog.WAREHOUSE_ACTIVITY.equals(eventMessage.getEventHeader().getEventType())) {
                return;
            }
            
            WarehouseActivityEvent event = objectMapper.readValue(  
                eventMessage.getEventBody(),
                WarehouseActivityEvent.class
            );
            
            log.info("Processing warehouse activity: {} tons of {} from warehouse: {} with action: {}", 
            event.amount(), event.materialType(), event.warehouseNumber(), event.action());
        
            // Handle both PAYLOAD_DELIVERED and LOADING_VESSEL
            switch (event.action()) {
                case "PAYLOAD_DELIVERED" -> handlePayloadDelivered(event);
                case "LOADING_VESSEL" -> handleLoadingVessel(event);
                default -> log.warn("Unknown warehouse activity action: {}", event.action());
            }
            
        } catch (Exception e) {
            log.error("Error processing warehouse activity event: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing warehouse activity event", e);
        }
    }

    private void handlePayloadDelivered(WarehouseActivityEvent event) {
        
        log.info("Received payload delivered event: {} tons of {} in warehouse: {} - StorageTracking already created by PDT listener", 
        event.amount(), event.materialType(), event.warehouseNumber());
    }

    private void handleLoadingVessel(WarehouseActivityEvent event) {
        // Apply FIFO deduction
        applyFIFODeduction(event.warehouseNumber(), event.materialType(), event.amount());
    }
    
    private void applyFIFODeduction(String warehouseNumber, String materialType, double amountToDeduct) {
        List<StorageTracking> storageRecords = storageTrackingRepositoryPort
            .findByWarehouseAndMaterialOrderByDeliveryTime(warehouseNumber, materialType);
        
        double remainingToDeduct = amountToDeduct;
        
        for (StorageTracking record : storageRecords) {
            if (remainingToDeduct <= 0) break;
            
            if (record.hasRemainingTons()) {
                double tonsToDeduct = Math.min(remainingToDeduct, record.getRemainingTons());

                // update existing record instead of creating new ones
                record.deductTons(tonsToDeduct);

                
                // Save the updated record
                storageTrackingRepositoryPort.save(record);
                
                remainingToDeduct -= tonsToDeduct;
                
                log.info("FIFO deduction: {} tons from PDT {} ({} tons remaining)", 
                    tonsToDeduct, record.getPdtId(), record.getRemainingTons());
            }
        }
        
        if (remainingToDeduct > 0) {
            log.warn("Could not deduct {} tons - insufficient stock in warehouse: {} for material: {}", 
                remainingToDeduct, warehouseNumber, materialType);
        }
    }
}