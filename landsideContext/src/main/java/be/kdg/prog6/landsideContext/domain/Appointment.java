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
    private LocalDateTime actualArrivalTime;
    
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
    
    public void markAsArrived(LocalDateTime arrivalTime) {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Appointment must be in SCHEDULED status to mark as arrived");
        }
        
        if (!arrivalWindow.isWithinWindow(arrivalTime)) {
            throw new IllegalArgumentException("Arrival time is outside the scheduled window");
        }
        
        this.actualArrivalTime = arrivalTime;
        this.status = AppointmentStatus.ARRIVED;
    }
    
    public boolean isRecognized() {
        return status == AppointmentStatus.ARRIVED;
    }
    
    public String getFormattedCreatedAt() {
        return createdAt.format(DATE_TIME_FORMATTER);
    }
    
    public String getFormattedArrivalWindow() {
        return arrivalWindow.getFormattedStartTime() + " - " + arrivalWindow.getFormattedEndTime();
    }
    
    public String getFormattedActualArrivalTime() {
        return actualArrivalTime != null ? actualArrivalTime.format(DATE_TIME_FORMATTER) : "Not arrived yet";
    }
} 