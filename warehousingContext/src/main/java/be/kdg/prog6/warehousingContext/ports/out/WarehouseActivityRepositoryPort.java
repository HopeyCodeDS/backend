package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseActivityRepositoryPort {
    void save(WarehouseActivity activity);
    List<WarehouseActivity> findByWarehouseId(UUID warehouseId);
    List<WarehouseActivity> findByWarehouseIdAndTimeRange(UUID warehouseId, LocalDateTime from, LocalDateTime to);
    Optional<WarehouseActivity> findById(UUID activityId);
    List<WarehouseActivity> findRecentActivities(UUID warehouseId, int limit);
    void deleteAll();
}