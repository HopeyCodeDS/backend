package be.kdg.prog6.warehousingContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.PurchaseOrderMatchedWithShippingOrderEvent;
import be.kdg.prog6.warehousingContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderFulfillmentRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderMatchedAMQPListener {
    
    private final ObjectMapper objectMapper;
    private final PurchaseOrderFulfillmentRepositoryPort fulfillmentRepositoryPort;
    
    @RabbitListener(queues = RabbitMQModuleTopology.PO_MATCHED_WITH_SO_QUEUE)
    public void handlePurchaseOrderMatched(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if(!EventCatalog.PURCHASE_ORDER_MATCHED_WITH_SHIPPING_ORDER.equals(eventMessage.getEventHeader().getEventType())) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return;
            }

            // Deserialize the event body
            PurchaseOrderMatchedWithShippingOrderEvent event = objectMapper.readValue(
                    eventMessage.getEventBody(), 
                    PurchaseOrderMatchedWithShippingOrderEvent.class
            );
            
            log.info("Warehouse Manager received PurchaseOrderMatchedWithShippingOrderEvent for PO: {} with vessel: {}", 
                    event.purchaseOrderReference(), event.vesselNumber());
            
            // Update the PO fulfillment tracking with vessel number
            Optional<PurchaseOrderFulfillmentTracking> fulfillmentOpt = 
                fulfillmentRepositoryPort.findByPurchaseOrderNumber(event.purchaseOrderReference());
            
            if (fulfillmentOpt.isPresent()) {
                PurchaseOrderFulfillmentTracking fulfillment = fulfillmentOpt.get();
                
                // Update the vessel number in the fulfillment tracking
                fulfillment.markAsFulfilled(event.vesselNumber());
                fulfillmentRepositoryPort.save(fulfillment);
                
                log.info("Successfully updated PO fulfillment tracking for PO: {} with vessel: {}", 
                        event.purchaseOrderReference(), event.vesselNumber());
            } else {
                log.warn("PO fulfillment tracking not found for PO: {}", event.purchaseOrderReference());
            }
                    
        } catch (Exception e) {
            log.error("Failed to process PurchaseOrderMatchedWithShippingOrderEvent: {}", 
                    eventMessage.getEventHeader().getEventType(), e);
        }
    }
} 