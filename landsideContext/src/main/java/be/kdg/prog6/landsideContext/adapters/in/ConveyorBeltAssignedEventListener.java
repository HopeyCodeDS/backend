package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.common.events.ConveyorBeltAssignedEvent;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Slf4j
@Service
public class ConveyorBeltAssignedEventListener {

    private final TruckRepositoryPort truckRepositoryPort;

    public ConveyorBeltAssignedEventListener(TruckRepositoryPort truckRepositoryPort) {
        this.truckRepositoryPort = truckRepositoryPort;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "conveyorBeltAssignedQueue")
    public void onConveyorBeltAssigned(Message message) {
        try {
            String jsonMessage = new String(message.getBody());
            log.info("Raw message received from RabbitMQ: {}", jsonMessage);

            // Remove extra quotes if present
            if (jsonMessage.startsWith("\"") && jsonMessage.endsWith("\"")) {
                jsonMessage = jsonMessage.substring(1, jsonMessage.length() - 1);
                jsonMessage = jsonMessage.replace("\\\"", "\"");
            }

            ConveyorBeltAssignedEvent event = objectMapper.readValue(jsonMessage, ConveyorBeltAssignedEvent.class);
            log.info("Deserialized ConveyorBeltAssignedEvent: {}", event);

            // Process the event
            processConveyorBeltAssignedEvent(event);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize ConveyorBeltAssignedEvent message: {}", e.getMessage());
            log.debug("Deserialization error details:", e);
        } catch (Exception e) {
            log.error("Failed to process ConveyorBeltAssignedEvent message: {}", e.getMessage());
            log.debug("Processing error details:", e);
        }
    }

    private void processConveyorBeltAssignedEvent(ConveyorBeltAssignedEvent event) {
        log.info("Processing ConveyorBeltAssignedEvent for license plate {}", event.getLicensePlate());

        Optional<Truck> truckOpt = truckRepositoryPort.findTruckByLicensePlate(event.getLicensePlate());
        if (truckOpt.isPresent()) {
            Truck truck = truckOpt.get();
            truck.setAssignedConveyorBelt(event.getConveyorBeltId());
            truck.dock();  // Mark truck as docked after assigning conveyor belt
            truckRepositoryPort.save(truck);
            log.info("Truck {} updated with assigned conveyor belt {}", event.getLicensePlate(), event.getConveyorBeltId());
        } else {
            log.warn("No truck found with license plate {}", event.getLicensePlate());
        }
    }


//    @RabbitListener(queues = "conveyorBeltAssignedQueue")
//    public void onConveyorBeltAssigned(ConveyorBeltAssignedEvent event) {
//        log.info("Processing ConveyorBeltAssignedEvent for license plate {}", event.getLicensePlate());
//
//        Optional<Truck> truckOpt = truckRepositoryPort.findTruckByLicensePlate(event.getLicensePlate());
//        if (truckOpt.isPresent()) {
//            Truck truck = truckOpt.get();
//            truck.setAssignedConveyorBelt(event.getConveyorBeltId());
//            truck.dock();  // Mark truck as docked after assigning conveyor belt
//            truckRepositoryPort.save(truck);
//            log.info("Truck {} updated with assigned conveyor belt {}", event.getLicensePlate(), event.getConveyorBeltId());
//        } else {
//            log.warn("No truck found with license plate {}", event.getLicensePlate());
//        }
//    }
}
