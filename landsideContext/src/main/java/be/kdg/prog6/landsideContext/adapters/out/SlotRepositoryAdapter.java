package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.SlotJpaEntity;
import be.kdg.prog6.landsideContext.adapters.out.db.repositories.SlotJpaRepository;
import be.kdg.prog6.landsideContext.domain.Slot;
import be.kdg.prog6.landsideContext.ports.out.SlotRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class SlotRepositoryAdapter implements SlotRepositoryPort {
    private final SlotJpaRepository slotJpaRepository;

    public SlotRepositoryAdapter(SlotJpaRepository slotJpaRepository) {
        this.slotJpaRepository = slotJpaRepository;
    }

    @Override
    public Optional<Slot> findSlotByStartTime(LocalDateTime startTime) {
        return slotJpaRepository.findSlotByStartTime(startTime)
                .map(this::mapToDomainSlot); // Convert to Slot domain object
    }

    @Override
    public void saveSlot(Slot slot) {
        SlotJpaEntity slotJpaEntity = mapToJpaEntity(slot); // Convert domain to JPA entity
        slotJpaRepository.save(slotJpaEntity);
    }

    @Override
    public Optional<Slot> findAvailableSlot(LocalDateTime arrivalTime) {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> findMaxSlotId() {
        return Optional.empty();
    }

    private Slot mapToDomainSlot(SlotJpaEntity slotJpaEntity) {
        Slot slot = new Slot(slotJpaEntity.getStartTime(), slotJpaEntity.getEndTime());
        slot.setId(slotJpaEntity.getSlotId()); // If Slot has an ID field in domain
        return slot;
    }

    private SlotJpaEntity mapToJpaEntity(Slot slot) {
        SlotJpaEntity slotJpaEntity = new SlotJpaEntity();
        slotJpaEntity.setStartTime(slot.getStartTime());
        slotJpaEntity.setEndTime(slot.getEndTime());
        return slotJpaEntity;
    }
}
