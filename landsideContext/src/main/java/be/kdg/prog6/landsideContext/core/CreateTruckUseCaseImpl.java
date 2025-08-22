package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.commands.CreateTruckCommand;
import be.kdg.prog6.landsideContext.ports.in.CreateTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateTruckUseCaseImpl implements CreateTruckUseCase {
    
    private final TruckRepositoryPort truckRepositoryPort;
    
    @Override
    @Transactional
    public Truck createTruck(CreateTruckCommand command) {
        log.info("Creating new truck with license plate: {}", command.getLicensePlate());
        
        // Check if truck with this license plate already exists
        if (truckRepositoryPort.findByLicensePlate(command.getLicensePlate()).isPresent()) {
            throw new IllegalArgumentException("Truck with license plate " + command.getLicensePlate() + " already exists");
        }
        
        Truck truck = command.toTruck();
        truckRepositoryPort.save(truck);
        
        log.info("Successfully created truck with ID: {} and license plate: {}", 
            truck.getTruckId(), truck.getLicensePlate().getValue());
        
        return truck;
    }
}