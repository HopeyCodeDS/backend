package be.kdg.prog6.landsideContext.domain.commands;

import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.Truck;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateTruckCommand {
    private final UUID commandId;
    private final String licensePlate;
    private final Truck.TruckType truckType;
    
    public UpdateTruckCommand(String licensePlate, Truck.TruckType truckType) {
        this.commandId = UUID.randomUUID();
        this.licensePlate = licensePlate;
        this.truckType = truckType;
    }
    
    public Truck toTruck(UUID truckId) {
        return new Truck(
            truckId,
            new LicensePlate(licensePlate),
            truckType
        );
    }
}
