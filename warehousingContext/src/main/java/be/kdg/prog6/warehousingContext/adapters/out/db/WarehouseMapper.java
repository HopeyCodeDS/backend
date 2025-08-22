package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.RawMaterial;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {
    
    public WarehouseJpaEntity toJpaEntity(Warehouse warehouse) {
        WarehouseJpaEntity jpaEntity = new WarehouseJpaEntity();
        jpaEntity.setWarehouseId(warehouse.getWarehouseId());
        jpaEntity.setWarehouseNumber(warehouse.getWarehouseNumber());
        jpaEntity.setSellerId(warehouse.getSellerId());
        jpaEntity.setRawMaterialName(warehouse.getAssignedMaterial().getName());
        jpaEntity.setRawMaterialPricePerTon(warehouse.getAssignedMaterial().getPricePerTon());
        jpaEntity.setRawMaterialStoragePricePerTonPerDay(warehouse.getAssignedMaterial().getStoragePricePerTonPerDay());
        jpaEntity.setCurrentCapacity(warehouse.getCurrentCapacity());
        jpaEntity.setMaxCapacity(warehouse.getMaxCapacity());
        return jpaEntity;
    }
    
    public Warehouse toDomain(WarehouseJpaEntity jpaEntity) {
        RawMaterial rawMaterial = new RawMaterial(
            jpaEntity.getRawMaterialName(),
            jpaEntity.getRawMaterialPricePerTon(),
            jpaEntity.getRawMaterialStoragePricePerTonPerDay()
        );

        Warehouse warehouse = new Warehouse(
            jpaEntity.getWarehouseId(),
            jpaEntity.getWarehouseNumber(),
            jpaEntity.getSellerId(),
            rawMaterial
        );
        
        // Setting the current capacity after construction
        warehouse.setCurrentCapacity(jpaEntity.getCurrentCapacity());
        
        return warehouse;
    }
}