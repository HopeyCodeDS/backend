package be.kdg.prog6.landsideContext.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Calendar {
    private final List<Slot> slots;

    public Calendar(List<Slot> slots) {
        this.slots = slots;
    }

    public Optional<Slot> findAvailableSlot(LocalDateTime arrivalTime) {
        // Find a slot where the arrival time matches start time and has available capacity
        return slots.stream()
                .filter(slot -> !slot.isFull() && slot.getStartTime().isEqual(arrivalTime))
                .findFirst();
    }

    public void bookSlot(Slot slot, Truck truck) {
        // attempting to book the slot for the truck, ensuring the slot has capacity
        slot.bookSlot(truck);
    }
}
