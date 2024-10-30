package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.Truck;

import java.util.Optional;

public interface TruckRepositoryPort {
    Optional<Truck> findTruckByLicensePlate(String licensePlate);
    void save(Truck truck);
}
