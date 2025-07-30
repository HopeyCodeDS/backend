package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.Truck;
import java.util.Optional;
import java.util.UUID;

public interface TruckRepositoryPort {
    void save(Truck truck);
    Optional<Truck> findById(UUID truckId);
    Optional<Truck> findByLicensePlate(String licensePlate);
} 