package be.kdg.prog6.landsideContext.domain.commands;

import be.kdg.prog6.landsideContext.domain.Truck;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ScheduleAppointmentCommand {
    private final UUID commandId;
    private final String sellerId;
    private final String licensePlate;
    private final Truck.TruckType truckType;
    private final String rawMaterialName;
    private final LocalDateTime arrivalTime;
    
    public ScheduleAppointmentCommand(String sellerId, String licensePlate, 
                                    Truck.TruckType truckType, String rawMaterialName, 
                                    LocalDateTime arrivalTime) {
        this.commandId = UUID.randomUUID();
        this.sellerId = sellerId;
        this.licensePlate = licensePlate;
        this.truckType = truckType;
        this.rawMaterialName = rawMaterialName;
        this.arrivalTime = arrivalTime;
    }
} 