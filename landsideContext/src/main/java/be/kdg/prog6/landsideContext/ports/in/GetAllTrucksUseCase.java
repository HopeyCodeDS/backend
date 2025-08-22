package be.kdg.prog6.landsideContext.ports.in;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import be.kdg.prog6.landsideContext.domain.Truck;

public interface GetAllTrucksUseCase {
    Optional<Truck> getTruckById(UUID truckId);
    List<Truck> getAllTrucks();
}
