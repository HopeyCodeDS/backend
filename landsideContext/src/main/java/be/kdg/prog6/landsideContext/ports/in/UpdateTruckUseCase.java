package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.commands.UpdateTruckCommand;

import java.util.UUID;

public interface UpdateTruckUseCase {
    Truck updateTruck(UUID truckId, UpdateTruckCommand command);
}
