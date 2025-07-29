package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
public class Appointment {
    private final UUID appointmentId;
    private final String sellerId;
    private final Truck truck;
    private final RawMaterial rawMaterial;
    private final ArrivalWindow arrivalWindow;
    private final LocalDateTime createdAt;
    private AppointmentStatus status;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public Appointment(UUID appointmentId, String sellerId, Truck truck, 
                      RawMaterial rawMaterial, ArrivalWindow arrivalWindow) {
        this.appointmentId = appointmentId;
        this.sellerId = sellerId;
        this.truck = truck;
        this.rawMaterial = rawMaterial;
        this.arrivalWindow = arrivalWindow;
        this.createdAt = LocalDateTime.now();
        this.status = AppointmentStatus.SCHEDULED;
    }
    
    public void markAsArrived() {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Appointment must be in SCHEDULED status to mark as arrived");
        }
        this.status = AppointmentStatus.ARRIVED;
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
    
    public String getFormattedCreatedAt() {
        return createdAt.format(DATE_TIME_FORMATTER);
    }
    
    public String getFormattedArrivalWindow() {
        return arrivalWindow.getFormattedStartTime() + " - " + arrivalWindow.getFormattedEndTime();
    }
} 