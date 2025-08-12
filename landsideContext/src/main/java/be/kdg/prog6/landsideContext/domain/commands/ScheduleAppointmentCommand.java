package be.kdg.prog6.landsideContext.domain.commands;

import be.kdg.prog6.landsideContext.domain.Truck;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ScheduleAppointmentCommand {
    private final UUID commandId;
    private final String sellerId;
    private final Truck truck;
    private final String rawMaterialName;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime scheduledTime;
    
    public ScheduleAppointmentCommand(String sellerId, Truck truck, String rawMaterialName, 
                                    LocalDateTime scheduledTime) {
        this.commandId = UUID.randomUUID();
        this.sellerId = sellerId;
        this.truck = truck;
        this.rawMaterialName = rawMaterialName;
        this.scheduledTime = scheduledTime;
    }
} 