package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.WarehouseStatus;
import be.kdg.prog6.landsideContext.ports.in.GetWarehouseStatusUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWarehouseStatusUseCaseImpl implements GetWarehouseStatusUseCase {
    
    private final TruckMovementRepositoryPort truckMovementRepository;
    
    @Override
    public WarehouseStatus getWarehouseStatus(String licensePlate) {
        var movement = truckMovementRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Truck movement not found"));

        String status = movement.getAssignedWarehouse() != null ? "COMPLETED" : "PROCESSING";
        String message = movement.getAssignedWarehouse() != null ? 
                "Warehouse assigned successfully" : 
                "Warehouse assignment in progress";

        return new WarehouseStatus(status, message, movement.getAssignedWarehouse());
    }
}
