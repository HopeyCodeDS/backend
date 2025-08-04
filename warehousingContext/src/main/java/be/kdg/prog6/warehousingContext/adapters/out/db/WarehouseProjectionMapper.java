package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.WarehouseProjection;
import org.springframework.stereotype.Component;

@Component("warehouseProjectionMapper")
public class WarehouseProjectionMapper {

    public WarehouseProjection toDomain(WarehouseProjectionJpaEntity entity) {
        return new WarehouseProjection(
            entity.getWarehouseId(),
            entity.getWarehouseNumber(),
            entity.getSellerId(),
            entity.getAssignedMaterial(),
            entity.getMaxCapacity(),
            entity.getCurrentCapacity()
        );
    }

    public WarehouseProjectionJpaEntity toJpaEntity(WarehouseProjection warehouseProjection) {
        WarehouseProjectionJpaEntity jpaEntity = new WarehouseProjectionJpaEntity();
        jpaEntity.setWarehouseId(warehouseProjection.getWarehouseId());
        jpaEntity.setWarehouseNumber(warehouseProjection.getWarehouseNumber());
        jpaEntity.setSellerId(warehouseProjection.getSellerId());
        jpaEntity.setAssignedMaterial(warehouseProjection.getAssignedMaterial());
        jpaEntity.setMaxCapacity(warehouseProjection.getMaxCapacity());
        jpaEntity.setCurrentCapacity(warehouseProjection.getCurrentCapacity());
        return jpaEntity;
    }
}
