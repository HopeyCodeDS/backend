package be.kdg.prog6.landsideContext.domain.commands;

import be.kdg.prog6.landsideContext.domain.Truck;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ScheduleAppointmentCommand {
    private final UUID commandId;
    private final String sellerId;
    private final Truck truck;
    private final String rawMaterialName;
    private final LocalDateTime arrivalTime;
    
    public ScheduleAppointmentCommand(String sellerId, Truck truck, String rawMaterialName, 
                                    LocalDateTime arrivalTime) {
        this.commandId = UUID.randomUUID();
        this.sellerId = sellerId;
        this.truck = truck;
        this.rawMaterialName = rawMaterialName;
        this.arrivalTime = arrivalTime;
    }
} 