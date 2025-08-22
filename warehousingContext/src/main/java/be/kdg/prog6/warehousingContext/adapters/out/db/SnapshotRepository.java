package be.kdg.prog6.warehousingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SnapshotRepository extends JpaRepository<SnapshotEntity, UUID> {
    
    Optional<SnapshotEntity> findTopByAggregateIdOrderByVersionDesc(UUID aggregateId);
}
