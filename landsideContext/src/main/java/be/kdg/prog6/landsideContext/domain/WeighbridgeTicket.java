package be.kdg.prog6.landsideContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class WeighbridgeTicket {
    private final UUID ticketId;
    private final String licensePlate;
    private final double grossWeight;
    private final double tareWeight;
    private final double netWeight;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime weighingTime;
    
    public WeighbridgeTicket(String licensePlate, double grossWeight, double tareWeight, LocalDateTime weighingTime) {
        this.ticketId = UUID.randomUUID();
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = grossWeight - tareWeight;
        this.weighingTime = weighingTime;
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
    
    public boolean isValidWeighing() {
        return grossWeight > 0 && tareWeight > 0 && netWeight > 0;
    }
} 