package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.ports.in.UpdateTruckExitWeighbridgeUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateTruckExitWeighbridgeUseCaseImpl implements UpdateTruckExitWeighbridgeUseCase {
    
    private final TruckMovementRepositoryPort truckMovementRepositoryPort;
    
    @Override
    @Transactional
    public void updateTruckExitWeighbridge(String licensePlate, String exitWeighbridgeNumber) {
        log.info("Updating exit weighing bridge for truck: {} to: {}", licensePlate, exitWeighbridgeNumber);
        
        Optional<TruckMovement> truckMovementOpt = truckMovementRepositoryPort.findByLicensePlate(licensePlate);
        
        if (truckMovementOpt.isEmpty()) {
            log.error("Truck movement not found for license plate: {}", licensePlate);
            throw new IllegalStateException("Truck movement not found: " + licensePlate);
        }
        
        TruckMovement truckMovement = truckMovementOpt.get();
        
        // Update truck movement with exit weighing bridge number
        truckMovement.setExitWeighbridgeNumber(exitWeighbridgeNumber);
        
        // Save updated movement
        truckMovementRepositoryPort.save(truckMovement);
        
        log.info("Successfully updated truck {} with exit weighing bridge: {}", 
            licensePlate, exitWeighbridgeNumber);
    }
} 