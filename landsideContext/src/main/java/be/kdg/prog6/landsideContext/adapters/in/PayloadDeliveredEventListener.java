package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.events.PayloadReceivedEvent;
import be.kdg.prog6.landsideContext.adapters.out.RabbitMQConfig;
import be.kdg.prog6.landsideContext.domain.TruckDispatchRecord;
import be.kdg.prog6.landsideContext.ports.out.TruckDispatchRecordRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayloadDeliveredEventListener {

    private static final Logger log = LoggerFactory.getLogger(PayloadDeliveredEventListener.class);
    private final TruckDispatchRecordRepositoryPort truckDispatchRecordRepositoryPort;
    private final ObjectMapper objectMapper;

    @Autowired
    public PayloadDeliveredEventListener(TruckDispatchRecordRepositoryPort truckDispatchRecordRepositoryPort, ObjectMapper objectMapper) {
        this.truckDispatchRecordRepositoryPort = truckDispatchRecordRepositoryPort;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.DELIVERY_COMPLETED_QUEUE_NAME)
    public void onPayloadDelivered(Message message) {
        try {
            String jsonMessage = new String(message.getBody());
            log.info("Raw message received from RabbitMQ: {}", jsonMessage);

            // Remove extra quotes if present
            if (jsonMessage.startsWith("\"") && jsonMessage.endsWith("\"")) {
                jsonMessage = jsonMessage.substring(1, jsonMessage.length() - 1);
                jsonMessage = jsonMessage.replace("\\\"", "\"");
            }

            PayloadReceivedEvent event = objectMapper.readValue(jsonMessage, PayloadReceivedEvent.class);
            log.info("Deserialized PayloadReceivedEvent: {}", event);

            // Process the event
            processPayloadReceivedEvent(event);
        } catch (Exception e) {
            log.error("Failed to process PayloadReceivedEvent message: {}", e.getMessage());
            log.debug("Processing error details:", e);
        }
    }

    private void processPayloadReceivedEvent(PayloadReceivedEvent event) {
        log.info("Processing PayloadReceivedEvent for license plate: {}", event.getLicensePlate());
        log.info("Processing PayloadReceivedEvent At this timestamp: {}", event.getDeliveryTime());

        // Reconstruct and save the PDT in Landside Context
        try {
            // Convert String materialType to MaterialType enum
            MaterialType materialType = MaterialType.valueOf(event.getMaterialType());

            // Reconstruct and save the PDT in Landside Context
            TruckDispatchRecord pdt = new TruckDispatchRecord(
                    event.getLicensePlate(),
                    materialType,
                    event.getConveyorBeltId(),
                    event.getWeighingBridgeNumber(),
                    event.getWeight(),
                    event.getDeliveryTime()
            );
            truckDispatchRecordRepositoryPort.save(pdt);
            log.info("Saved copy of PDT for truck with license plate: {}", event.getLicensePlate());
        } catch (IllegalArgumentException e) {
            log.error("Invalid material type received: {}", event.getMaterialType());
        } catch (Exception e) {
            log.error("Error processing PayloadReceivedEvent: {}", e.getMessage());
        }
    }
}
