package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class ArrivalComplianceDto {
    UUID appointmentId;
    String licensePlate;
    UUID sellerId;
    String rawMaterial;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime scheduledArrivalTime;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime actualArrivalTime;
    
    String status;
    boolean isOnTime;
} 