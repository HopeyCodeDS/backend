package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.WarehouseProjection;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseProjectionPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarehouseProjectionDatabaseAdapter implements WarehouseProjectionPort {
    
    private final WarehouseProjectionRepository repository;
    private final WarehouseProjectionMapper mapper;
    
    @Override
    public Optional<WarehouseProjection> loadWarehouseProjection(UUID warehouseId) {
        return repository.findById(warehouseId)
            .map(mapper::toDomain);
    }
    
    @Override
    public void saveWarehouseProjection(WarehouseProjection warehouseProjection) {
        WarehouseProjectionJpaEntity entity = mapper.toJpaEntity(warehouseProjection);
        repository.save(entity);
    }
} 