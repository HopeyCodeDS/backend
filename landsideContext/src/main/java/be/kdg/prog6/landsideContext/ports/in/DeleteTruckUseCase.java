package be.kdg.prog6.landsideContext.ports.in;

import java.util.UUID;

public interface DeleteTruckUseCase {
    void deleteTruck(UUID truckId);
}
