package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
public class WeighbridgeTicket {
    private final UUID ticketId;
    private final String licensePlate;
    private final double grossWeight;
    private final double tareWeight;
    private final double netWeight;
    private final LocalDateTime weighingTime;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public WeighbridgeTicket(String licensePlate, double grossWeight, double tareWeight) {
        this.ticketId = UUID.randomUUID();
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = grossWeight - tareWeight;
        this.weighingTime = LocalDateTime.now();
    }
    
    public WeighbridgeTicket(UUID ticketId, String licensePlate, double grossWeight, double tareWeight, 
                            double netWeight, LocalDateTime weighingTime) {
        this.ticketId = ticketId;
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = netWeight;
        this.weighingTime = weighingTime;
    }
    
    public String getFormattedWeighingTime() {
        return weighingTime.format(DATE_FORMATTER);
    }
    
    public boolean isValidWeighing() {
        return grossWeight > 0 && tareWeight > 0 && netWeight > 0;
    }
} 