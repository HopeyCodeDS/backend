    package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import org.springframework.stereotype.Component;

@Component
public class WarehouseActivityMapper {
    
    public WarehouseActivityJpaEntity toEntity(WarehouseActivity activity) {
        WarehouseActivityJpaEntity entity = new WarehouseActivityJpaEntity();
        entity.setActivityId(activity.getActivityId());
        entity.setWarehouseId(activity.getWarehouseId());
        entity.setAmount(activity.getAmount());
        entity.setAction(activity.getAction());
        entity.setPointInTime(activity.getPointInTime());
        entity.setMaterialType(activity.getMaterialType());
        entity.setLicensePlate(activity.getLicensePlate());
        entity.setDescription(activity.getDescription());
        return entity;
    }
    
    public WarehouseActivity toDomain(WarehouseActivityJpaEntity entity) {
        return new WarehouseActivity(
            entity.getWarehouseId(),
            entity.getAmount(),
            entity.getAction(),
            entity.getPointInTime(),
            entity.getMaterialType(),
            entity.getLicensePlate(),
            entity.getDescription()
        );
    }
} 