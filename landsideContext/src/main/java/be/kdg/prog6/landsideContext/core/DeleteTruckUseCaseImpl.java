package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.in.DeleteTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteTruckUseCaseImpl implements DeleteTruckUseCase {
    
    private final TruckRepositoryPort truckRepositoryPort;
    
    @Override
    @Transactional
    public void deleteTruck(UUID truckId) {
        log.info("Deleting truck with ID: {}", truckId);
        
        Truck truck = truckRepositoryPort.findById(truckId)
            .orElseThrow(() -> new IllegalArgumentException("Truck not found with ID: " + truckId));
        
        truckRepositoryPort.deleteById(truckId);
        
        log.info("Successfully deleted truck with ID: {} and license plate: {}", 
            truckId, truck.getLicensePlate().getValue());
    }
}
