package be.kdg.prog6.warehousingContext.adapters.in;

import be.kdg.prog6.common.events.ConveyorBeltAssignedEvent;
import be.kdg.prog6.common.events.PayloadReceivedEvent;
import be.kdg.prog6.common.events.TruckDispatchedEvent;
import be.kdg.prog6.warehousingContext.adapters.out.RabbitMQEventPublisher;
import be.kdg.prog6.warehousingContext.domain.ConveyorBelt;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.in.ConveyorBeltAssignmentUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PayloadDeliveryRecordRepositoryPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class PayloadDeliveryEventListener {
    private final Logger log = LoggerFactory.getLogger(PayloadDeliveryEventListener.class);

    public static final String DELIVERY_INITIATED_QUEUE_NAME = "deliveryInitiatedQueue";

    private final ConveyorBeltAssignmentUseCase conveyorBeltAssignmentUseCase;
    private final PayloadDeliveryRecordRepositoryPort payloadDeliveryRecordRepositoryPort;
    private final RabbitMQEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public PayloadDeliveryEventListener(ConveyorBeltAssignmentUseCase conveyorBeltAssignmentUseCase,
                                        PayloadDeliveryRecordRepositoryPort payloadDeliveryRecordRepositoryPort,
                                        RabbitMQEventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.conveyorBeltAssignmentUseCase = conveyorBeltAssignmentUseCase;
        this.payloadDeliveryRecordRepositoryPort = payloadDeliveryRecordRepositoryPort;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = DELIVERY_INITIATED_QUEUE_NAME)
    public void onPayloadDeliveryInitiated(Message message) {
        try {
            String jsonMessage = new String(message.getBody());
            log.info("Raw message received from RabbitMQ: {}", jsonMessage);

            // Remove extra quotes if present
            if (jsonMessage.startsWith("\"") && jsonMessage.endsWith("\"")) {
                jsonMessage = jsonMessage.substring(1, jsonMessage.length() - 1);
                jsonMessage = jsonMessage.replace("\\\"", "\"");
            }

            TruckDispatchedEvent event = objectMapper.readValue(jsonMessage, TruckDispatchedEvent.class);
            log.info("Deserialized TruckDispatchedEvent: {}", event);

            handleTruckDispatchedEvent(event);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize TruckDispatchedEvent message: {}", e.getMessage());
            log.debug("Deserialization error details:", e);
        } catch (Exception e) {
            log.error("Failed to process TruckDispatchedEvent message: {}", e.getMessage());
            log.debug("Processing error details:", e);
        }
    }


    // Separate method to handle the event once deserialized, for better organization
    private void handleTruckDispatchedEvent(TruckDispatchedEvent event) {
        // Assign conveyor belt based on material type
        ConveyorBelt conveyorBelt = conveyorBeltAssignmentUseCase.assignConveyorBelt(event.getMaterialType());

        // Get warehouse ID (assuming it is part of the ConveyorBelt assignment context)
        String warehouseId = event.getWarehouseId(); // Get warehouse ID
        log.info("This conveyorBelt number {} is found in warehouse {}", conveyorBelt.getBeltNumber(), warehouseId);

        // Create and save PayloadDeliveryTicket (optional)
        PayloadDeliveryTicket pdt = new PayloadDeliveryTicket(
                event.getLicensePlate(),
                event.getMaterialType(),
                conveyorBelt.getBeltNumber(),
                event.getWeighingBridgeNumber(),
                event.getWeight(),
                warehouseId
        );

        payloadDeliveryRecordRepositoryPort.save(pdt);

        // Publish ConveyorBeltAssignedEvent after successful assignment
        eventPublisher.publishConveyorBeltAssignedEvent(new ConveyorBeltAssignedEvent(
                event.getLicensePlate(),
                conveyorBelt.getBeltNumber(),
                event.getTimestamp(),
                event.getWarehouseId()
        ));
        log.info("Published ConveyorBeltAssignedEvent for truck with license plate {}", event.getLicensePlate());

        // Publish PayloadReceivedEvent after PDT creation
        eventPublisher.publishPayloadDeliveredEvent(new PayloadReceivedEvent(
                event.getLicensePlate(),
                conveyorBelt.getBeltNumber(),
                pdt.getDeliveryTime(),
                pdt.getMaterialType().name(),
                pdt.getWeighingBridgeNumber(),
                event.getWeight(),
                event.getWarehouseId())
        );
        log.info("Published PayloadReceivedEvent for license plate {} now assigned to warehouseID: {}", event.getLicensePlate(), event.getWarehouseId());
    }
}
