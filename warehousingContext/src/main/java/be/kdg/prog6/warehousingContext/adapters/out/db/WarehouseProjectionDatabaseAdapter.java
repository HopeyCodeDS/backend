package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.WarehouseProjection;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseProjectionPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        log.info("Loading warehouse projection for warehouse: {}", warehouseId);
        return repository.findById(warehouseId)
            .map(mapper::toDomain);
    }
    
    @Override
    @Transactional
    public void saveWarehouseProjection(WarehouseProjection warehouseProjection) {
        try {
            // Console logging for debugging
            System.out.println("=== SAVING TO WAREHOUSE_PROJECTION TABLE ===");
            System.out.printf("Warehouse ID: %s\n", warehouseProjection.getWarehouseId());
            System.out.printf("Warehouse Number: %s\n", warehouseProjection.getWarehouseNumber());
            System.out.printf("Seller ID: %s\n", warehouseProjection.getSellerId());
            System.out.printf("Assigned Material: %s\n", warehouseProjection.getAssignedMaterial());
            System.out.printf("Max Capacity: %s tons\n", warehouseProjection.getMaxCapacity());
            System.out.printf("Current Capacity: %s tons\n", warehouseProjection.getCurrentCapacity());
            System.out.printf("Available Space: %s tons\n", warehouseProjection.getMaxCapacity() - warehouseProjection.getCurrentCapacity());
            double utilization = (warehouseProjection.getCurrentCapacity() / warehouseProjection.getMaxCapacity()) * 100.0;
            System.out.printf("Utilization: %.2f%%\n", utilization);
            
            WarehouseProjectionJpaEntity entity = mapper.toJpaEntity(warehouseProjection);
            log.info("Created JPA entity for warehouse: {} with capacity: {} tons", 
                entity.getWarehouseId(), entity.getCurrentCapacity());
            
            WarehouseProjectionJpaEntity savedEntity = repository.save(entity);
            log.info("Successfully saved warehouse projection to database for warehouse: {}", 
                savedEntity.getWarehouseId());
            System.out.println("=== WAREHOUSE_PROJECTION TABLE UPDATE COMPLETE ===");
        } catch (Exception e) {
            log.error("Failed to save warehouse projection for warehouse: {}", 
                warehouseProjection.getWarehouseId(), e);
            throw e;
        }
    }
} 