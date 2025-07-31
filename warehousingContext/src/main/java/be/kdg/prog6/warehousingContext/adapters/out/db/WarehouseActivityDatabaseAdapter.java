package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WarehouseActivityDatabaseAdapter implements WarehouseActivityRepositoryPort {
    
    private final WarehouseActivityRepository repository;
    private final WarehouseActivityMapper mapper;
    
    @Override
    public void save(WarehouseActivity activity) {
        WarehouseActivityJpaEntity entity = mapper.toEntity(activity);
        repository.save(entity);
    }
    
    @Override
    public List<WarehouseActivity> findByWarehouseId(UUID warehouseId) {
        return repository.findByWarehouseIdOrderByPointInTimeDesc(warehouseId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<WarehouseActivity> findByWarehouseIdAndTimeRange(UUID warehouseId, LocalDateTime from, LocalDateTime to) {
        return repository.findByWarehouseIdAndTimeRange(warehouseId, from, to)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public Optional<WarehouseActivity> findById(UUID activityId) {
        return repository.findById(activityId)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<WarehouseActivity> findRecentActivities(UUID warehouseId, int limit) {
        return repository.findRecentActivities(warehouseId, PageRequest.of(0, limit))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}