package be.kdg.prog6.warehousingContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.PurchaseOrderSubmitted;
import be.kdg.prog6.warehousingContext.ports.in.TrackPurchaseOrderFulfillmentUseCase;
import be.kdg.prog6.warehousingContext.domain.commands.TrackPurchaseOrderFulfillmentCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
    
@Component
@RequiredArgsConstructor
@Slf4j
public class WarehousingPurchaseOrderSubmittedAMQPListener {
    
    private final ObjectMapper objectMapper;
    private final TrackPurchaseOrderFulfillmentUseCase trackPurchaseOrderFulfillmentUseCase;
    
    @RabbitListener(queues = "purchase-order.submitted.warehousing")
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
            
            log.info("Warehouse Manager received PurchaseOrderSubmitted event for PO: {} from customer: {} (Event Type: {})", 
                    event.purchaseOrderNumber(), 
                    event.customerName(),
                    eventMessage.getEventHeader().getEventType());

            // Convert order lines to command format
            List<TrackPurchaseOrderFulfillmentCommand.OrderLine> orderLines = event.orderLines().stream()
                    .map(line -> new TrackPurchaseOrderFulfillmentCommand.OrderLine(
                            line.rawMaterialName(),
                            line.amountInTons(),
                            line.pricePerTon()))
                    .collect(Collectors.toList());
            
            // Track the PO for fulfillment monitoring with orderlines
            TrackPurchaseOrderFulfillmentCommand command = new TrackPurchaseOrderFulfillmentCommand(
                event.purchaseOrderNumber(),
                event.customerNumber(),
                event.customerName(),
                event.totalValue(),
                event.submittedAt(),
                orderLines
            );
            
            trackPurchaseOrderFulfillmentUseCase.trackNewPurchaseOrder(command);
            
            // Process the purchase order for warehousing planning
            processPurchaseOrderForWarehousing(event);
            
            log.info("Successfully processed and tracked PurchaseOrderSubmitted event for PO in the warehouse context: {}", 
                    event.purchaseOrderNumber());
                    
        } catch (Exception e) {
            log.error("Failed to process PurchaseOrderSubmitted event: {}", 
                    eventMessage.getEventHeader().getEventID(), e);
        }
    }
    
    private void processPurchaseOrderForWarehousing(PurchaseOrderSubmitted event) {
        // Business logic for warehousing planning:
        // 1. Check if we have enough inventory in the warehouse for the order
        // 2. Plan warehouse capacity for upcoming shipment
        // 3. Prepare for shipment operations
        
        double totalMaterialsNeeded = event.orderLines().stream()
                .mapToDouble(PurchaseOrderSubmitted.PurchaseOrderLine::amountInTons)
                .sum();
        
        log.info("Planning warehousing for PO: {} - Total materials needed: {} tons", 
                event.purchaseOrderNumber(), totalMaterialsNeeded);

        // Log detailed material requirements
        event.orderLines().forEach(line -> 
            log.info("Material requirement for PO {}: {} tons of {} at ${}/ton", 
                event.purchaseOrderNumber(), line.amountInTons(), line.rawMaterialName(), line.pricePerTon()));
    }
} 