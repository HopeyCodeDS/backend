package be.kdg.prog6.warehousingContext.adapters.out.persistence;

import be.kdg.prog6.warehousingContext.ports.out.EventStorePort;
import be.kdg.prog6.warehousingContext.domain.WarehouseSnapshot;
import be.kdg.prog6.warehousingContext.adapters.out.db.EventStoreEntity;
import be.kdg.prog6.warehousingContext.adapters.out.db.SnapshotEntity;
import be.kdg.prog6.warehousingContext.adapters.out.db.EventStoreRepository;
import be.kdg.prog6.warehousingContext.adapters.out.db.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventStoreAdapter implements EventStorePort {
    
    private final EventStoreRepository eventStoreRepository;
    private final SnapshotRepository snapshotRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void saveEvents(UUID aggregateId, List<Object> events, long expectedVersion) {
        for (int i = 0; i < events.size(); i++) {
            Object event = events.get(i);
            EventStoreEntity eventEntity = new EventStoreEntity();
            eventEntity.setEventId(UUID.randomUUID());
            eventEntity.setAggregateId(aggregateId);
            eventEntity.setEventType(event.getClass().getSimpleName());
            eventEntity.setEventData(serializeEvent(event));
            eventEntity.setVersion(expectedVersion + i + 1);
            eventEntity.setTimestamp(LocalDateTime.now());
            
            eventStoreRepository.save(eventEntity);
        }
    }

    @Override
    public List<Object> getEvents(UUID aggregateId) {
        List<EventStoreEntity> events = eventStoreRepository.findByAggregateIdOrderByVersion(aggregateId);
        return events.stream()
            .map(this::deserializeEvent)
            .toList();
    }

    @Override
    public List<Object> getEventsSince(UUID aggregateId, long version) {
        List<EventStoreEntity> events = eventStoreRepository.findByAggregateIdAndVersionGreaterThanOrderByVersion(aggregateId, version);
        return events.stream()
            .map(this::deserializeEvent)
            .toList();
    }

    @Override
    public void saveSnapshot(UUID aggregateId, WarehouseSnapshot snapshot) {
        SnapshotEntity snapshotEntity = new SnapshotEntity();
        snapshotEntity.setSnapshotId(UUID.randomUUID());
        snapshotEntity.setAggregateId(aggregateId);
        snapshotEntity.setSnapshotData(serializeSnapshot(snapshot));
        snapshotEntity.setVersion(snapshot.version());
        snapshotEntity.setTimestamp(snapshot.timestamp());
        
        snapshotRepository.save(snapshotEntity);
    }

    @Override
    public WarehouseSnapshot getLatestSnapshot(UUID aggregateId) {
        return snapshotRepository.findTopByAggregateIdOrderByVersionDesc(aggregateId)
            .map(this::deserializeSnapshot)
            .orElse(null);
    }

    private String serializeEvent(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }

    private Object deserializeEvent(EventStoreEntity eventEntity) {
        try {
            
            return objectMapper.readValue(eventEntity.getEventData(), Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize event", e);
        }
    }

    private String serializeSnapshot(WarehouseSnapshot snapshot) {
        try {
            return objectMapper.writeValueAsString(snapshot);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize snapshot", e);
        }
    }

    private WarehouseSnapshot deserializeSnapshot(SnapshotEntity snapshotEntity) {
        try {
            return objectMapper.readValue(snapshotEntity.getSnapshotData(), WarehouseSnapshot.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize snapshot", e);
        }
    }
}
