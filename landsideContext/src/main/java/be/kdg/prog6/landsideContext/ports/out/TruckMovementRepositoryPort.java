package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.TruckMovement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
 
public interface TruckMovementRepositoryPort {
    void save(TruckMovement truckMovement);
    Optional<TruckMovement> findById(UUID movementId);
    Optional<TruckMovement> findByLicensePlate(String licensePlate);
    List<TruckMovement> findAllOnSite();
    long countTrucksOnSite();
} 