package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.WarehouseSnapshot;
import java.util.List;
import java.util.UUID;

public interface EventStorePort {
    void saveEvents(UUID aggregateId, List<Object> events, long expectedVersion);
    List<Object> getEvents(UUID aggregateId);
    List<Object> getEventsSince(UUID aggregateId, long version);
    void saveSnapshot(UUID aggregateId, WarehouseSnapshot snapshot);
    WarehouseSnapshot getLatestSnapshot(UUID aggregateId);
}
