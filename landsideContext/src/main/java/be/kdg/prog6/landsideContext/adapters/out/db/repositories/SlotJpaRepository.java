package be.kdg.prog6.landsideContext.adapters.out.db.repositories;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.SlotJpaEntity;
import be.kdg.prog6.landsideContext.domain.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SlotJpaRepository extends JpaRepository<SlotJpaEntity, Long> {
    Optional<SlotJpaEntity> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime startTime, LocalDateTime endTime);

    Optional<Slot> findByStartTime(LocalDateTime startTime);

    @Query("select s from SlotJpaEntity s order by s.slotId DESC")
    Optional<SlotJpaEntity> findTopByOrderBySlotIdDesc();
    Optional<SlotJpaEntity> findSlotByStartTime(LocalDateTime startTime);
}
