package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.WarehouseAssignment;
import org.springframework.stereotype.Component;

@Component
public class WarehouseAssignmentMapper {
    
    public WarehouseAssignmentJpaEntity toJpaEntity(WarehouseAssignment domain) {
        return new WarehouseAssignmentJpaEntity(
            domain.getAssignmentId(),
            domain.getWarehouseId(),
            domain.getLicensePlate(),
            domain.getWarehouseNumber(),
            domain.getRawMaterialName(),
            domain.getSellerId(),
            domain.getTruckWeight(),
            domain.getAssignedAt()
        );
    }
    
    public WarehouseAssignment toDomain(WarehouseAssignmentJpaEntity jpaEntity) {
        return new WarehouseAssignment(
            jpaEntity.getAssignmentId(),
            jpaEntity.getWarehouseId(),
            jpaEntity.getLicensePlate(),
            jpaEntity.getWarehouseNumber(),
            jpaEntity.getRawMaterialName(),
            jpaEntity.getSellerId(),
            jpaEntity.getTruckWeight(),
            jpaEntity.getAssignedAt()
        );
    }
} 