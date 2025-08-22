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
    private final String sellerName;
    private final Truck truck;
    private final RawMaterial rawMaterial;
    private final ArrivalWindow arrivalWindow;
    private AppointmentStatus status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime scheduledTime;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime actualArrivalTime;
    
    
    public Appointment(UUID appointmentId, UUID sellerId, String sellerName, Truck truck, 
                      RawMaterial rawMaterial, ArrivalWindow arrivalWindow, LocalDateTime scheduledTime) {
        this.appointmentId = appointmentId;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.truck = truck;
        this.rawMaterial = rawMaterial;
        this.arrivalWindow = arrivalWindow;
        this.status = AppointmentStatus.SCHEDULED;
        this.scheduledTime = scheduledTime; 
    }

    public static Appointment schedule(UUID sellerId, String sellerName, Truck truck,
                                       RawMaterial rawMaterial, ArrivalWindow window,
                                       LocalDateTime scheduledTime) {
        if (sellerId == null) throw new IllegalArgumentException("Seller ID is required");
        if (sellerName == null) throw new IllegalArgumentException("Seller name is required");
        if (truck == null) throw new IllegalArgumentException("Truck is required");
        if (rawMaterial == null) throw new IllegalArgumentException("Raw material is required");
        if (scheduledTime == null) throw new IllegalArgumentException("Scheduled time is required");
        if (!window.isWithinWindow(scheduledTime)) {
            throw new IllegalArgumentException("Scheduled time must be within arrival window");
        }

        Appointment appointment = new Appointment(UUID.randomUUID(), sellerId, sellerName, truck, rawMaterial, window, scheduledTime);

        // the window enforce capacity check
        window.addAppointment(appointment);

        return appointment;
    }
    
    public void markAsArrived(LocalDateTime actualArrivalTime) {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Appointment must be in SCHEDULED status to mark as arrived");
        }
        this.status = AppointmentStatus.ARRIVED;
        this.actualArrivalTime = actualArrivalTime; 
    }

    public void markAsDeparted() {
        if (status != AppointmentStatus.ARRIVED) {
            throw new IllegalStateException("Appointment must be in ARRIVED status to mark as departed");
        }
        this.status = AppointmentStatus.DEPARTED;
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