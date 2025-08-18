package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.TruckLocation;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import be.kdg.prog6.landsideContext.domain.commands.AssignWeighingBridgeCommand;
import be.kdg.prog6.landsideContext.ports.in.AssignWeighingBridgeUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.WeighingBridgeAssignedPort;
import be.kdg.prog6.landsideContext.ports.out.WeighingBridgeRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignWeighingBridgeUseCaseImpl implements AssignWeighingBridgeUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final WeighingBridgeRepositoryPort weighingBridgeRepositoryPort;
    private final TruckMovementRepositoryPort truckMovementRepositoryPort;
    private final WeighingBridgeAssignedPort weighingBridgeAssignedPort;

    @Override
    public String assignWeighingBridge(AssignWeighingBridgeCommand command) {
        log.info("Starting weighing bridge assignment for license plate: {}", command.getLicensePlate());
        
        try {
            // Find appointment by license plate that is in ARRIVED status
            Optional<Appointment> appointmentOpt = appointmentRepositoryPort
                .findByLicensePlate(command.getLicensePlate())
                .stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.ARRIVED)
                .findFirst();

            if (appointmentOpt.isEmpty()) {
                throw new IllegalStateException("No arrived appointment found for license plate: " + command.getLicensePlate());
            }

            // Find truck movement by license plate
            Optional<TruckMovement> movementOpt = truckMovementRepositoryPort
                .findByLicensePlate(command.getLicensePlate());

            if (movementOpt.isEmpty()) {
                throw new IllegalStateException("No truck movement found for license plate: " + command.getLicensePlate());
            }

            TruckMovement truckMovement = movementOpt.get();

            // Check if truck is at gate
            if (truckMovement.getCurrentLocation() != TruckLocation.GATE) {
                throw new IllegalStateException("Truck must be at gate to assign weighing bridge");
            }
            
            // Find available weighing bridge
            List<WeighingBridge> availableBridges = weighingBridgeRepositoryPort.findAvailableBridges();

            if (availableBridges.isEmpty()) {
                throw new IllegalStateException("No available weighing bridges found");
            }

            // Get the first available weighing bridge
            WeighingBridge availableBridge = availableBridges.get(0);
            log.info("Found available bridge: {}", availableBridge.getBridgeNumber());

            // Assign the available bridge to truck movement first
            truckMovement.assignWeighingBridge(availableBridge.getBridgeNumber(), command.getAssignmentTime());
            log.info("Assigned bridge {} to truck movement", availableBridge.getBridgeNumber());

            // Save the updated truck movement
            truckMovementRepositoryPort.save(truckMovement);
            log.info("Saved truck movement with assigned bridge");

            // Publish event through output port
            try {
                weighingBridgeAssignedPort.weighingBridgeAssigned(truckMovement);
            } catch (Exception e) {
                log.error("Failed to publish weighing bridge assigned event", e);
            }

            log.info("Successfully assigned weighing bridge {} to truck {}", 
                    availableBridge.getBridgeNumber(), command.getLicensePlate());
            
            return availableBridge.getBridgeNumber();
            
        } catch (Exception e) {
            log.error("Error during weighing bridge assignment", e);
            throw e;
        }
    }
}
