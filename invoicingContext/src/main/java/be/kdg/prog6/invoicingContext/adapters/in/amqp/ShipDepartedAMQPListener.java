package be.kdg.prog6.invoicingContext.adapters.in.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import be.kdg.prog6.invoicingContext.ports.out.PurchaseOrderRepositoryPort;
// import be.kdg.prog6.invoicingContext.core.CommissionCalculationUseCase;
import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.common.events.ShipDepartedEvent;
import lombok.extern.slf4j.Slf4j;
import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipDepartedAMQPListener {
    
    private final PurchaseOrderRepositoryPort purchaseOrderRepositoryPort;
    // private final CommissionCalculationUseCase commissionCalculationUseCase;
    private final ObjectMapper objectMapper;

    // This method is called when a ship departed event is received
    @RabbitListener(queues = "ship.departed.queue")
    public void handleShipDeparted(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if(!EventCatalog.SHIP_DEPARTED.equals(eventMessage.getEventHeader().getEventType())) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return;
            }

            // Deserialize the event body
            ShipDepartedEvent event = objectMapper.readValue(
                eventMessage.getEventBody(),
                ShipDepartedEvent.class
            );

            log.info("Received and processed ship departed event in the invoicing context: {} with PO reference: {}", 
                event.shippingOrderId(), event.purchaseOrderReference());

            // Find purchase order and mark as fulfilled
            Optional<PurchaseOrder> poOpt = purchaseOrderRepositoryPort.findByPurchaseOrderNumber(event.purchaseOrderReference());
            
            if (poOpt.isPresent()) {
                PurchaseOrder po = poOpt.get();
                po.markAsFulfilled();
                purchaseOrderRepositoryPort.save(po);

                log.info("Purchase order {} marked as fulfilled in the invoicing context.", po.getPurchaseOrderNumber());
                // Calculate commission fee
                // commissionCalculationUseCase.calculateCommissionForFulfilledOrder(po);
            }
        
        } catch (Exception e) {
            log.error("Unexpected error processing ship departed event: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error processing ship departed event", e);
        }
    }
} 