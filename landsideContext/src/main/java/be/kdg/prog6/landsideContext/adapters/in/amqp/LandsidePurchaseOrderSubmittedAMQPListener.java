package be.kdg.prog6.landsideContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.PurchaseOrderSubmitted;
import be.kdg.prog6.landsideContext.adapters.config.RabbitMQModuleTopology;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LandsidePurchaseOrderSubmittedAMQPListener {
    
    private final ObjectMapper objectMapper;
    
    @RabbitListener(queues = RabbitMQModuleTopology.PURCHASE_ORDER_SUBMITTED_QUEUE)
    public void handlePurchaseOrderSubmitted(EventMessage eventMessage) {
        try {
            // Deserialize the event body using shared kernel
            PurchaseOrderSubmitted event = objectMapper.readValue(
                    eventMessage.getEventBody(), 
                    PurchaseOrderSubmitted.class
            );
            
            log.info("Received PurchaseOrderSubmitted event for PO: {} from customer: {} (Event ID: {})", 
                    event.purchaseOrderNumber(), 
                    event.customerName(),
                    eventMessage.getEventHeader().getEventID());
            
            // Process the purchase order for truck scheduling
            processPurchaseOrderForTruckScheduling(event);
            
            log.info("Successfully processed PurchaseOrderSubmitted event for PO: {}", 
                    event.purchaseOrderNumber());
                    
        } catch (Exception e) {
            log.error("Failed to process PurchaseOrderSubmitted event: {}", 
                    eventMessage.getEventHeader().getEventID(), e);
        }
    }
    
    private void processPurchaseOrderForTruckScheduling(PurchaseOrderSubmitted event) {
        // Business logic for truck scheduling:
        // 1. Plan truck capacity needed for the order
        // 2. Schedule truck appointments for material pickup
        // 3. Prepare weighing bridge assignments
        
        double totalMaterialsNeeded = event.orderLines().stream()
                .mapToDouble(PurchaseOrderSubmitted.PurchaseOrderLine::amountInTons)
                .sum();
        
        log.info("Planning truck scheduling for PO: {} - Total materials to transport: {} tons", 
                event.purchaseOrderNumber(), totalMaterialsNeeded);
    }
} 