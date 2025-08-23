package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.ports.in.AssignWarehouseToTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignWarehouseToTruckUseCaseImpl implements AssignWarehouseToTruckUseCase {

    private final TruckMovementRepositoryPort truckMovementRepository;

    @Override
    @Transactional
    public void assignWarehouseToTruck(String licensePlate, String warehouseNumber) {
        log.info("Assigning warehouse {} to truck: {}", warehouseNumber, licensePlate);

        TruckMovement movement = truckMovementRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Truck movement not found for license plate: " + licensePlate));

        // Validate truck is ready for warehouse assignment
        if (!movement.isReadyForWarehouseAssignment()) {
            throw new IllegalStateException("Truck " + licensePlate + " is not ready for warehouse assignment. Weight must be recorded first.");
        }

        // Assign warehouse to truck
        movement.assignWarehouse(warehouseNumber);
        log.info("Warehouse {} assigned to truck {}", warehouseNumber, licensePlate);

        // Save updated movement
        truckMovementRepository.save(movement);
        log.info("Truck movement updated and saved for truck: {}", licensePlate);
    }
}