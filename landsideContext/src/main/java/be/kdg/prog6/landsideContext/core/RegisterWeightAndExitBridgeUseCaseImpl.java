package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.TruckLocation;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.commands.RegisterWeightAndExitBridgeCommand;
import be.kdg.prog6.landsideContext.ports.in.RegisterWeightAndExitBridgeUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.TruckLeftWeighingBridgePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterWeightAndExitBridgeUseCaseImpl implements RegisterWeightAndExitBridgeUseCase {
    
    private final TruckMovementRepositoryPort truckMovementRepository;
    private final TruckLeftWeighingBridgePort truckLeftWeighingBridgePort;
    private final AppointmentRepositoryPort appointmentRepository;
    
    @Override
    @Transactional
    public void registerWeightAndExitBridge(RegisterWeightAndExitBridgeCommand command) {
        TruckMovement movement = truckMovementRepository.findByLicensePlate(command.getLicensePlate())
                .orElseThrow(() -> new IllegalArgumentException("Truck movement not found"));
        
        // Validate current location
        if (movement.getCurrentLocation() != TruckLocation.WEIGHING_BRIDGE) {
            throw new IllegalStateException("Truck must be at weighing bridge to register weight and exit");
        }
        
                // Register weight
        movement.recordWeighing(command.getWeight());
        
        // Exit weighing bridge
        movement.leaveWeighingBridge();
        
        // Save updated movement
        truckMovementRepository.save(movement);

        // Get appointment for this truck to get sellerId
        var appointment = appointmentRepository.findByLicensePlate(command.getLicensePlate())
        .stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        
        // Publish event for Warehousing Context
        truckLeftWeighingBridgePort.truckLeftWeighingBridge(movement, command.getRawMaterialName(), appointment.getSellerId());
        log.info("Truck left weighing bridge event published for truck: {}", command.getLicensePlate());
    }

    // Method to handle warehouse assignment response
    @Transactional
    public void assignWarehouseToTruck(String licensePlate, String warehouseNumber) {
        TruckMovement movement = truckMovementRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Truck movement not found"));
        
        // Assign warehouse to truck
        movement.assignWarehouse(warehouseNumber);
        
        // Save updated movement
        truckMovementRepository.save(movement);

        // Log the update
        log.info("Updated truck movement for {} with warehouse: {}", licensePlate, warehouseNumber);
    }
} 