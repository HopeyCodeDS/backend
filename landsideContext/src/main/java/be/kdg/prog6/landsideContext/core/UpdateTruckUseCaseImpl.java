package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.commands.UpdateTruckCommand;
import be.kdg.prog6.landsideContext.ports.in.UpdateTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateTruckUseCaseImpl implements UpdateTruckUseCase {
    
    private final TruckRepositoryPort truckRepositoryPort;
    
    @Override
    @Transactional
    public Truck updateTruck(UUID truckId, UpdateTruckCommand command) {
        log.info("Updating truck with ID: {}", truckId);
        
        Truck existingTruck = truckRepositoryPort.findById(truckId)
            .orElseThrow(() -> new IllegalArgumentException("Truck not found with ID: " + truckId));
        
        // Check if the new license plate conflicts with another truck
        if (!existingTruck.getLicensePlate().getValue().equals(command.getLicensePlate())) {
            if (truckRepositoryPort.findByLicensePlate(command.getLicensePlate()).isPresent()) {
                throw new IllegalArgumentException("Truck with license plate " + command.getLicensePlate() + " already exists");
            }
        }
        
        Truck updatedTruck = command.toTruck(truckId);
        truckRepositoryPort.save(updatedTruck);
        
        log.info("Successfully updated truck with ID: {} and license plate: {}", 
            truckId, updatedTruck.getLicensePlate().getValue());
        
        return updatedTruck;
    }
}
