package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.StorageTracking;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class StorageTrackingJpaMapper {
    
    private static final Logger log = LoggerFactory.getLogger(StorageTrackingJpaMapper.class);

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
        
        entity.setStorageCostCalculationDate(domain.getStorageCostCalculationDate());
        entity.setNumberOfDays(domain.getNumberOfDays());
        entity.setCostInDollars(BigDecimal.valueOf(domain.getCostInDollars()));
        entity.setStorageCost(BigDecimal.valueOf(domain.getStorageCost()));
        entity.setIsSellerTracking(domain.isSellerTracking());
        return entity;
    }
    
    public StorageTracking toDomain(StorageTrackingJpaEntity entity) {
        StorageTracking domain = new StorageTracking(
            entity.getTrackingId(),
            entity.getWarehouseNumber(),
            entity.getCustomerNumber(),
            entity.getMaterialType(),
            entity.getTonsStored(),
            entity.getDeliveryTime(),
            entity.getPdtId(),
            entity.getRemainingTons(),
            entity.getStorageCostCalculationDate(),
            entity.getNumberOfDays() != null ? entity.getNumberOfDays() : 0,
            entity.getCostInDollars() != null ? entity.getCostInDollars().doubleValue() : 0.0,
            entity.getStorageCost() != null ? entity.getStorageCost().doubleValue() : 0.0,
            entity.isSellerTracking()
        );
        
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