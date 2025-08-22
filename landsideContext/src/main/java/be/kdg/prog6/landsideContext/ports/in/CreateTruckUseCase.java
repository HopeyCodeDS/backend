package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.commands.CreateTruckCommand;
import be.kdg.prog6.landsideContext.domain.Truck;

public interface CreateTruckUseCase {
    Truck createTruck(CreateTruckCommand command);
}
