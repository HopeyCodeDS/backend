package be.kdg.prog6.watersideContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.PurchaseOrderSubmitted;
import be.kdg.prog6.watersideContext.adapters.out.invoicing.PurchaseOrderValidationAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WatersidePurchaseOrderSubmittedAMQPListener {
    
    private final ObjectMapper objectMapper;
    private final PurchaseOrderValidationAdapter purchaseOrderValidationAdapter;
    
    @RabbitListener(queues = "purchase-order.submitted.waterside")
    public void handlePurchaseOrderSubmitted(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if(!EventCatalog.PURCHASE_ORDER_SUBMITTED.equals(eventMessage.getEventHeader().getEventType())) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return;
            }

            // Deserialize the event body
            PurchaseOrderSubmitted event = objectMapper.readValue(
                    eventMessage.getEventBody(), 
                    PurchaseOrderSubmitted.class
            );
            
            log.info("Foreman received PurchaseOrderSubmitted event for PO: {} from customer: {} (Event Type: {})", 
                    event.purchaseOrderNumber(), 
                    event.customerName(),
                    eventMessage.getEventHeader().getEventType());
            
            // Add the PO to validation cache for foreman matching
            purchaseOrderValidationAdapter.addValidatedPurchaseOrder(
                event.purchaseOrderNumber(),
                event.customerNumber(),
                event.customerName()
            );
            
            // Process the purchase order for waterside operations
            processPurchaseOrderForWaterside(event);
            
            log.info("Successfully processed and cached PurchaseOrderSubmitted event for PO in the waterside context: {} - Cache size: {}", 
                    event.purchaseOrderNumber(), purchaseOrderValidationAdapter.getCacheSize());
                    
        } catch (Exception e) {
            log.error("Failed to process PurchaseOrderSubmitted event: {}", 
                    eventMessage.getEventHeader().getEventID(), e);
        }
    }
    
    private void processPurchaseOrderForWaterside(PurchaseOrderSubmitted event) {
        
        double totalMaterialsNeeded = event.orderLines().stream()
                .mapToDouble(PurchaseOrderSubmitted.PurchaseOrderLine::amountInTons)
                .sum();
        
        log.info("Planning waterside operations for PO: {} - Total materials needed: {} tons, Customer: {}", 
                event.purchaseOrderNumber(), totalMaterialsNeeded, event.customerName());
        
        // Additional waterside-specific processing
        log.info("PO {} is now available for SO/PO matching when vessel arrives at loading quay", 
                event.purchaseOrderNumber());
    }
}
