package be.kdg.prog6.landsideContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Appointment {
    private final UUID appointmentId;
    private final UUID sellerId;
    private final Truck truck;
    private final RawMaterial rawMaterial;
    private final ArrivalWindow arrivalWindow;
    private AppointmentStatus status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime scheduledTime;
    
    
    public Appointment(UUID appointmentId, UUID sellerId, Truck truck, 
                      RawMaterial rawMaterial, ArrivalWindow arrivalWindow, LocalDateTime scheduledTime) {
        this.appointmentId = appointmentId;
        this.sellerId = sellerId;
        this.truck = truck;
        this.rawMaterial = rawMaterial;
        this.arrivalWindow = arrivalWindow;
        this.status = AppointmentStatus.SCHEDULED;
        this.scheduledTime = scheduledTime; 
    }
    
    public void markAsArrived(LocalDateTime scheduledTime) {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Appointment must be in SCHEDULED status to mark as arrived");
        }
        this.status = AppointmentStatus.ARRIVED;
        this.scheduledTime = scheduledTime; 
    }
    
    public void cancel() {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Only scheduled appointments can be cancelled");
        }
        this.status = AppointmentStatus.CANCELLED;
    }
    
    public boolean isScheduled() {
        return status == AppointmentStatus.SCHEDULED;
    }
    
    public boolean isArrived() {
        return status == AppointmentStatus.ARRIVED;
    }
    
    public boolean isOnTime() {
        if (scheduledTime == null) {
            return false;
        }
        return arrivalWindow.isWithinWindow(scheduledTime);
    }
} 