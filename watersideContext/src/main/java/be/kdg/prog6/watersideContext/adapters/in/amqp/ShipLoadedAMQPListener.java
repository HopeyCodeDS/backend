package be.kdg.prog6.watersideContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.ShipLoadedEvent;
import be.kdg.prog6.watersideContext.ports.in.CompleteLoadingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipLoadedAMQPListener {
    
    private final CompleteLoadingUseCase completeLoadingUseCase;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "ship.loaded.queue")
    public void handleShipLoaded(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if(!EventCatalog.SHIP_LOADED.equals(eventMessage.getEventHeader().getEventType())) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return;
            }

            // Deserialize the event body
            ShipLoadedEvent event = objectMapper.readValue(
                eventMessage.getEventBody(),
                ShipLoadedEvent.class
            );

            log.info("Received and processed ship loaded event: {} with PO reference: {}", 
                event.shippingOrderId(), event.purchaseOrderReference());

            // Complete the loading process
            completeLoadingUseCase.completeLoading(
                event.shippingOrderId(),
                event.loadingCompletionDate()
            );
            
        } catch (Exception e) {
            log.error("Failed to process ship loaded event: {}", e.getMessage(), e);
        }
    }
} 