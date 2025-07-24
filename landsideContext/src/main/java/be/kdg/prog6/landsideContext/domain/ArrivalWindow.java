package be.kdg.prog6.landsideContext.domain;

import lombok.Value;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Value
public class ArrivalWindow {
    LocalDateTime startTime;
    LocalDateTime endTime;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
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
    
    public String getFormattedStartTime() {
        return startTime.format(DATE_TIME_FORMATTER);
    }
    
    public String getFormattedEndTime() {
        return endTime.format(DATE_TIME_FORMATTER);
    }
} 