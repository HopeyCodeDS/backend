package be.kdg.prog6.landsideContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import java.time.LocalDateTime;

@Value
public class ArrivalWindow {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime startTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime endTime;
    
    public ArrivalWindow(LocalDateTime scheduledTime) {
        // Arrival window is exactly 1 hour starting from the scheduled time
        this.startTime = scheduledTime.withSecond(0).withNano(0);
        this.endTime = this.startTime.plusHours(1);
    }
    
    public boolean isWithinWindow(LocalDateTime arrivalTime) {
        return !arrivalTime.isBefore(startTime) && !arrivalTime.isAfter(endTime);
    }
    
    public boolean overlapsWith(ArrivalWindow other) {
        return !this.endTime.isBefore(other.startTime) && !other.endTime.isBefore(this.startTime);
    }
} 