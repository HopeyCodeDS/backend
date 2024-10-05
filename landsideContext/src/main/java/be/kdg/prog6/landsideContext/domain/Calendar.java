package be.kdg.prog6.landsideContext.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Calendar {
    private List<Slot> slots;

    public Calendar(List<Slot> slots) {
        this.slots = slots;
    }

    public Optional<Slot> findAvailableSlot(LocalDateTime arrivalWindow) {
        return slots.stream()
                .filter(slot -> !slot.isBooked() && slot.getStartTime().isBefore(arrivalWindow)
                        && slot.getEndTime().isAfter(arrivalWindow))
                .findFirst();
    }

    public void bookSlot(Slot slot) {
        slot.bookSlot();
    }
}
