package be.kdg.prog6.warehousingContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.ShipReadyForLoadingEvent;
import be.kdg.prog6.warehousingContext.ports.in.AllocateOldestStockUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipReadyForLoadingAMQPListener {
    
    private final AllocateOldestStockUseCase allocateOldestStockUseCase;
    private final ObjectMapper objectMapper;

    
    @RabbitListener(queues = "ship.ready.for.loading.queue")
    public void handleShipReadyForLoading(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if(!EventCatalog.LOADING_STARTED.equals(eventMessage.getEventHeader().getEventType())) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return;
            }

            // Deserialize the event body
            ShipReadyForLoadingEvent event = objectMapper.readValue(
                eventMessage.getEventBody(),
                ShipReadyForLoadingEvent.class
            );

            log.info("Received and processed ship ready for loading event: {} with PO reference: {}", 
                event.getShippingOrderId(), event.getPurchaseOrderReference());

            // This will automatically allocate and deduct oldest stock 
            // (thereby adjusting the warehouse volume accordingly)
            allocateOldestStockUseCase.allocateAndDeductOldestStockForShippingOrder(
                event.getShippingOrderId(), 
                event.getPurchaseOrderReference()
            );
            
            log.info("Automatic warehouse volume adjustment completed based on shipping order: {}", 
                event.getShippingOrderId());
            
        } catch (Exception e) {
            log.error("Failed to process ship ready for loading event: {}", e.getMessage(), e);
        }
    }
}