package be.kdg.prog6.warehousingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventStoreRepository extends JpaRepository<EventStoreEntity, UUID> {
    
    List<EventStoreEntity> findByAggregateIdOrderByVersion(UUID aggregateId);
    
    @Query("SELECT e FROM EventStoreEntity e WHERE e.aggregateId = :aggregateId AND e.version > :version ORDER BY e.version")
    List<EventStoreEntity> findByAggregateIdAndVersionGreaterThanOrderByVersion(@Param("aggregateId") UUID aggregateId, @Param("version") long version);
}
