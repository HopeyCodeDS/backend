package be.kdg.prog6.warehousingContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.PurchaseOrderSubmitted;
import be.kdg.prog6.warehousingContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.warehousingContext.ports.in.TrackPurchaseOrderFulfillmentUseCase;
import be.kdg.prog6.warehousingContext.domain.commands.TrackPurchaseOrderFulfillmentCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarehousingPurchaseOrderSubmittedAMQPListener {
    
    private final ObjectMapper objectMapper;
    private final TrackPurchaseOrderFulfillmentUseCase trackPurchaseOrderFulfillmentUseCase;
    
    @RabbitListener(queues = RabbitMQModuleTopology.PURCHASE_ORDER_SUBMITTED_QUEUE)
    public void handlePurchaseOrderSubmitted(EventMessage eventMessage) {
        try {
            // Deserialize the event body
            PurchaseOrderSubmitted event = objectMapper.readValue(
                    eventMessage.getEventBody(), 
                    PurchaseOrderSubmitted.class
            );
            
            log.info("Received PurchaseOrderSubmitted event for PO: {} from customer: {} (Event ID: {})", 
                    event.purchaseOrderNumber(), 
                    event.customerName(),
                    eventMessage.getEventHeader().getEventType());
            
            // Track the PO for fulfillment monitoring
            TrackPurchaseOrderFulfillmentCommand command = new TrackPurchaseOrderFulfillmentCommand(
                event.purchaseOrderNumber(),
                event.customerNumber(),
                event.customerName(),
                event.totalValue(),
                event.orderDate()
            );
            
            trackPurchaseOrderFulfillmentUseCase.trackNewPurchaseOrder(command);
            
            // Process the purchase order for warehousing planning
            processPurchaseOrderForWarehousing(event);
            
            log.info("Successfully processed and tracked PurchaseOrderSubmitted event for PO: {}", 
                    event.purchaseOrderNumber());
                    
        } catch (Exception e) {
            log.error("Failed to process PurchaseOrderSubmitted event: {}", 
                    eventMessage.getEventHeader().getEventID(), e);
        }
    }
    
    private void processPurchaseOrderForWarehousing(PurchaseOrderSubmitted event) {
        // Business logic for warehousing planning:
        // 1. Check if we have enough inventory for the order
        // 2. Plan warehouse capacity for upcoming shipment
        // 3. Prepare for shipment operations
        
        double totalMaterialsNeeded = event.orderLines().stream()
                .mapToDouble(PurchaseOrderSubmitted.PurchaseOrderLine::amountInTons)
                .sum();
        
        log.info("Planning warehousing for PO: {} - Total materials needed: {} tons", 
                event.purchaseOrderNumber(), totalMaterialsNeeded);
    }
} 