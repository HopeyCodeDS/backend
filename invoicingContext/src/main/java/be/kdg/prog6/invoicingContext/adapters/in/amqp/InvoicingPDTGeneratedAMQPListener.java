package be.kdg.prog6.invoicingContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.PDTGenerated;
import be.kdg.prog6.invoicingContext.domain.StorageTracking;
import be.kdg.prog6.invoicingContext.ports.out.StorageTrackingRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvoicingPDTGeneratedAMQPListener {
    
    private final StorageTrackingRepositoryPort storageTrackingRepositoryPort;
    private final ObjectMapper objectMapper;
    
    @RabbitListener(queues = "invoicing.pdt-generated-events")
    public void handlePDTGenerated(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if (!EventCatalog.PDT_GENERATED.equals(eventMessage.getEventHeader().getEventType())) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return;
            }
            
            // Deserialize the event body
            PDTGenerated event = objectMapper.readValue(
                eventMessage.getEventBody(),
                PDTGenerated.class
            );
            
            log.info("Received PDT generated event in invoicing context: {} for warehouse: {} with {} tons of {}", 
                event.pdtId(), event.warehouseNumber(), event.payloadWeight(), event.rawMaterialName());
            
            // Create storage tracking record
            StorageTracking storageTracking = new StorageTracking(
                event.warehouseNumber(),
                event.sellerId(), // This is the customer number for invoicing
                event.rawMaterialName(),
                event.payloadWeight(),
                event.deliveryTime(),
                event.pdtId()
            );
            
            // Save storage tracking
            storageTrackingRepositoryPort.save(storageTracking);
            
            log.info("Storage tracking created for PDT: {} with {} tons of {} in warehouse: {}", 
                event.pdtId(), event.payloadWeight(), event.rawMaterialName(), event.warehouseNumber());
            
        } catch (Exception e) {
            log.error("Error processing PDT generated event: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing PDT generated event", e);
        }
    }
} 