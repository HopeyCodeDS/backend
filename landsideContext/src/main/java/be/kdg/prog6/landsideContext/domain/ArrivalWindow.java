package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.landsideContext.domain.exceptions.CapacityExceededException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
public class ArrivalWindow {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime startTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime endTime;

    List<Appointment> appointments = new ArrayList<>();

    
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

    public boolean hasCapacity() {
        return appointments.size() < 40;
    }

    public void addAppointment(Appointment appointment) {
        if (!hasCapacity()) {
            throw new CapacityExceededException("Max 40 trucks per hour exceeded for window: " + startTime + " - " + endTime);
        }
        appointments.add(appointment);
    }
} 