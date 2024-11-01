package be.kdg.prog6.warehousingContext.adapters.out.db;


import be.kdg.prog6.warehousingContext.adapters.out.db.entities.WarehouseJpaEntity;
import be.kdg.prog6.warehousingContext.adapters.out.db.repositories.WarehouseJpaRepository;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class WarehouseRepositoryAdapter implements WarehouseRepositoryPort {

    private final WarehouseJpaRepository warehouseJpaRepository;

    public WarehouseRepositoryAdapter(WarehouseJpaRepository warehouseJpaRepository) {
        this.warehouseJpaRepository = warehouseJpaRepository;
    }

    @Override
    public Optional<Warehouse> findByWarehouseId(String warehouseId) {
        log.info("Find warehouse by warehouseId: {}", warehouseId);
        Optional<WarehouseJpaEntity> warehouseJpaEntity = warehouseJpaRepository.findByWarehouseId(warehouseId);

        // Manual mapping from WarehouseJpaEntity to Warehouse (Domain entity)
        return warehouseJpaEntity.map(warehouseEntity -> {
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseId(warehouseEntity.getWarehouseId());
            warehouse.setCustomerId(warehouseEntity.getCustomerId());
            warehouse.setMaterialType(warehouseEntity.getMaterialType());
            warehouse.setCapacity(warehouseEntity.getCapacity());
            warehouse.setCurrentLoad(warehouseEntity.getCurrentLoad());
            return warehouse;
        });
    }

    @Override
    public void save(Warehouse warehouse) {
        log.info("Saving warehouse {}", warehouse);

        // Map to JPA entity
        WarehouseJpaEntity warehouseJpaEntity = new WarehouseJpaEntity();
        warehouseJpaEntity.setWarehouseId(warehouse.getWarehouseId());
        warehouseJpaEntity.setCustomerId(warehouse.getCustomerId());
        warehouseJpaEntity.setMaterialType(warehouse.getMaterialType());
        warehouseJpaEntity.setCapacity(warehouse.getCapacity());
        warehouseJpaEntity.setCurrentLoad(warehouse.getCurrentLoad());

        warehouseJpaRepository.save(warehouseJpaEntity);
        log.info("Warehouse {} saved", warehouse.getWarehouseId());
    }

    @Override
    public Optional<Warehouse> findByCustomerIdAndMaterialType(String customerId, String materialType) {
        log.info("Find warehouse by customerId: {} materialType: {}", customerId, materialType);
        Optional<WarehouseJpaEntity> warehouseJpaEntity = warehouseJpaRepository.findByCustomerIdAndMaterialType(customerId, materialType);

        // Manually map from
        return warehouseJpaEntity.map(warehouseEntity -> {
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseId(warehouseEntity.getWarehouseId());
            warehouse.setCustomerId(warehouseEntity.getCustomerId());
            warehouse.setMaterialType(warehouseEntity.getMaterialType());
            warehouse.setCapacity(warehouseEntity.getCapacity());
            warehouse.setCurrentLoad(warehouseEntity.getCurrentLoad());
            return warehouse;

        });
    }

    @Override
    public List<Warehouse> findAll() {
        log.info("Fetching all warehouses");

        // Map list of WarehouseJpaEntity to list of Warehouse (Domain entity)
        return warehouseJpaRepository.findAll().stream()
                .map(entity -> {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setWarehouseId(entity.getWarehouseId());
                    warehouse.setCustomerId(entity.getCustomerId());
                    warehouse.setMaterialType(entity.getMaterialType());
                    warehouse.setCapacity(entity.getCapacity());
                    warehouse.setCurrentLoad(entity.getCurrentLoad());
                    return warehouse;
                })
                .collect(Collectors.toList());
    }
}
