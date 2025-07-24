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
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public Appointment(UUID appointmentId, String sellerId, Truck truck, 
                      RawMaterial rawMaterial, ArrivalWindow arrivalWindow) {
        this.appointmentId = appointmentId;
        this.sellerId = sellerId;
        this.truck = truck;
        this.rawMaterial = rawMaterial;
        this.arrivalWindow = arrivalWindow;
        this.createdAt = LocalDateTime.now();
    }
    
    public String getFormattedCreatedAt() {
        return createdAt.format(DATE_TIME_FORMATTER);
    }
    
    public String getFormattedArrivalWindow() {
        return arrivalWindow.getFormattedStartTime() + " - " + arrivalWindow.getFormattedEndTime();
    }
} 