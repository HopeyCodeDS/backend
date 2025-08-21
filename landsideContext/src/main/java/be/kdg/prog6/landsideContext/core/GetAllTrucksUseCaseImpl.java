package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.in.GetAllTrucksUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllTrucksUseCaseImpl implements GetAllTrucksUseCase {
    
    private final TruckRepositoryPort truckRepositoryPort;
    
    @Override
    @Transactional(readOnly = true)
    public List<Truck> getAllTrucks() {
        log.info("Retrieving all trucks");
        List<Truck> trucks = truckRepositoryPort.findAll();
        log.info("Found {} trucks", trucks.size());
        return trucks;
    }

    @Override
    public Optional<Truck> getTruckById(UUID truckId) {
        log.info("Retrieving truck by ID: {}", truckId);
        return truckRepositoryPort.findById(truckId);
    }
}


