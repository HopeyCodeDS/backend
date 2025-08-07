package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.StorageTracking;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StorageTrackingJpaMapper {
    
    public StorageTrackingJpaEntity toJpaEntity(StorageTracking domain) {
        StorageTrackingJpaEntity entity = new StorageTrackingJpaEntity();
        entity.setTrackingId(domain.getTrackingId());
        entity.setWarehouseNumber(domain.getWarehouseNumber());
        entity.setCustomerNumber(domain.getCustomerNumber());
        entity.setMaterialType(domain.getMaterialType());
        entity.setTonsStored(domain.getTonsStored());
        entity.setRemainingTons(domain.getRemainingTons());
        entity.setDeliveryTime(domain.getDeliveryTime());
        entity.setPdtId(domain.getPdtId());
        return entity;
    }
    
    public StorageTracking toDomain(StorageTrackingJpaEntity entity) {
        StorageTracking domain = new StorageTracking(
            entity.getWarehouseNumber(),
            entity.getCustomerNumber(),
            entity.getMaterialType(),
            entity.getTonsStored(),
            entity.getDeliveryTime(),
            entity.getPdtId()
        );
        
        // Set remaining tons (this needs to be handled properly since it's mutable)
        // You might need to add a setter to StorageTracking or handle this differently
        return domain;
    }
    
    public Map<String, List<StorageTracking>> groupByWarehouseAndMaterial(List<StorageTrackingJpaEntity> entities) {
        return entities.stream()
            .map(this::toDomain)
            .collect(Collectors.groupingBy(
                tracking -> tracking.getWarehouseNumber() + ":" + tracking.getMaterialType()
            ));
    }
} 