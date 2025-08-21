package be.kdg.prog6.landsideContext.domain;

import lombok.Value;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class ArrivalComplianceData {
    UUID appointmentId;
    String licensePlate;
    UUID sellerId;
    String rawMaterial;
    LocalDateTime scheduledArrivalTime;
    LocalDateTime actualArrivalTime;
    String status;
    boolean isOnTime;
    
    public ArrivalComplianceData(Appointment appointment) {
        this.appointmentId = appointment.getAppointmentId();
        this.licensePlate = appointment.getTruck().getLicensePlate().getValue();
        this.sellerId = appointment.getSellerId();
        this.rawMaterial = appointment.getRawMaterial().getName();
        this.scheduledArrivalTime = appointment.getArrivalWindow().getStartTime();
        this.actualArrivalTime = appointment.getActualArrivalTime(); 
        this.status = appointment.getStatus().name();
        
        // the domain logic is used to determine if on time
        this.isOnTime = appointment.isOnTime();
    }
} 