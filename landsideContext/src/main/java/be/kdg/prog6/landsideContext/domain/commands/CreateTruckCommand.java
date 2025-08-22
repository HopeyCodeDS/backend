package be.kdg.prog6.landsideContext.domain.commands;

import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.Truck;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateTruckCommand {
    private final UUID commandId;
    private final String licensePlate;
    private final Truck.TruckType truckType;
    
    public CreateTruckCommand(String licensePlate, Truck.TruckType truckType) {
        this.commandId = UUID.randomUUID();
        this.licensePlate = licensePlate;
        this.truckType = truckType;
    }
    
    public Truck toTruck() {
        return new Truck(
            UUID.randomUUID(),
            new LicensePlate(licensePlate),
            truckType
        );
    }
}
