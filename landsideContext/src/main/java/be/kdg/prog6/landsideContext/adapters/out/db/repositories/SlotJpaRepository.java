package be.kdg.prog6.landsideContext.adapters.out.db.repositories;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.SlotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SlotJpaRepository extends JpaRepository<SlotJpaEntity, Long> {
    Optional<SlotJpaEntity> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime startTime, LocalDateTime endTime);
}
