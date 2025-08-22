package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.StorageFee;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class StorageFeeJpaMapper {
    
    public StorageFeeJpaEntity toJpaEntity(StorageFee domain) {
        StorageFeeJpaEntity entity = new StorageFeeJpaEntity();
        entity.setStorageFeeId(domain.getStorageFeeId());
        entity.setTotalDailyFee(BigDecimal.valueOf(domain.getTotalDailyFee()));
        entity.setCalculationDate(domain.getCalculationDate());
        entity.setWarehouseNumber(domain.getWarehouseNumber());
        entity.setMaterialType(domain.getMaterialType());
        entity.setSellerId(domain.getSellerId());
        entity.setTotalDeliveryBatches(domain.getTotalDeliveryBatches());
        return entity;
    }
    
    public StorageFee toDomain(StorageFeeJpaEntity entity) {
        return new StorageFee(
            entity.getCalculationDate(),
            entity.getWarehouseNumber(),
            entity.getMaterialType(),
            entity.getSellerId(),
            entity.getTotalDailyFee().doubleValue(),
            entity.getTotalDeliveryBatches()
        );
    }
} 