package be.kdg.prog6.landsideContext.domain.commands;

import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.Truck.TruckType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateAppointmentCommand {
    private final UUID appointmentId;
    private UUID sellerId;
    private String licensePlate;
    private TruckType truckType;
    private String rawMaterialName;
    private AppointmentStatus status;
    private LocalDateTime scheduledTime;
    
    public UpdateAppointmentCommand(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }
}
