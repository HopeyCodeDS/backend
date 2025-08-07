package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.StorageFee;
import org.springframework.stereotype.Component;

@Component
public class StorageFeeJpaMapper {
    
    public StorageFeeJpaEntity toJpaEntity(StorageFee domain) {
        StorageFeeJpaEntity entity = new StorageFeeJpaEntity();
        entity.setStorageFeeId(domain.getStorageFeeId());
        entity.setWarehouseNumber(domain.getWarehouseNumber());
        entity.setCustomerNumber(domain.getCustomerNumber());
        entity.setMaterialType(domain.getMaterialType());
        entity.setTotalTonsStored(domain.getTotalTonsStored());
        entity.setFeeAmount(domain.getFeeAmount());
        entity.setCalculationDate(domain.getCalculationDate());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setNumberOfPDTs(domain.getNumberOfPDTs());
        return entity;
    }
    
    public StorageFee toDomain(StorageFeeJpaEntity entity) {
        return new StorageFee(
            entity.getWarehouseNumber(),
            entity.getCustomerNumber(),
            entity.getMaterialType(),
            entity.getTotalTonsStored(),
            entity.getFeeAmount(),
            entity.getCalculationDate(),
            entity.getCreatedAt(),
            entity.getNumberOfPDTs()
        );
    }
} 