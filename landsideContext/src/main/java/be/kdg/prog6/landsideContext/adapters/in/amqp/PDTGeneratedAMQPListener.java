package be.kdg.prog6.landsideContext.adapters.in.amqp;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.landsideContext.ports.in.UpdateTruckExitWeighbridgeUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PDTGeneratedAMQPListener {
    
    private final ObjectMapper objectMapper;
    private final UpdateTruckExitWeighbridgeUseCase updateTruckExitWeighbridgeUseCase;
    
    @RabbitListener(queues = "pdt-generated-queue")
    public void handlePDTGeneratedEvent(EventMessage eventMessage) {
        try {
            // Check if the event is of the correct type
            if (eventMessage.getEventHeader().getEventType() != EventCatalog.PDT_GENERATED) {
                log.debug("Ignoring event of type: {}", eventMessage.getEventHeader().getEventType());
                return; // Skip processing if not the right event type
            }
                
            log.info("Processing received PDT_GENERATED event: {}", eventMessage.getEventHeader().getEventType());
            
            // Parse the event body to extract PDT information
            JsonNode pdtData = objectMapper.readTree(eventMessage.getEventBody());
            
            String licensePlate = pdtData.get("licensePlate").asText();
            String newWeighingBridgeNumber = pdtData.get("newWeighingBridgeNumber").asText();
            
            log.info("Updating truck {} with exit weighing bridge number: {}", 
                licensePlate, newWeighingBridgeNumber);
            
            // Update truck movement with exit weighing bridge number
            updateTruckExitWeighbridgeUseCase.updateTruckExitWeighbridge(licensePlate, newWeighingBridgeNumber);
            
            log.info("Successfully updated truck {} with exit weighing bridge number: {}", 
                licensePlate, newWeighingBridgeNumber);

        } catch (Exception e) {
            log.error("Error processing PDT_GENERATED event: {}", e.getMessage(), e);
        }
    }
} 