package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.Slot;

import java.time.LocalDateTime;
import java.util.Optional;


public interface SlotRepositoryPort {
    void saveSlot(Slot slot);
    Optional<Slot> findAvailableSlot(LocalDateTime arrivalTime);

    Optional<Integer> findMaxSlotId();

    Optional<Slot> findSlotByStartTime(LocalDateTime startTime);
}
