package be.kdg.prog6.landsideContext.domain;

import java.time.LocalDateTime;

public class Slot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked;

    public Slot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = false;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookSlot() {
        if (isBooked) {
            throw new IllegalStateException("Slot is already booked.");
        }
        this.isBooked = true;
    }
}