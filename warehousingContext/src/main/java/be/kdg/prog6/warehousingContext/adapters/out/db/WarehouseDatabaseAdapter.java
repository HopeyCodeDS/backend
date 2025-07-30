package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WarehouseDatabaseAdapter implements WarehouseRepositoryPort {
    
    private final WarehouseJpaRepository warehouseJpaRepository;
    private final WarehouseMapper warehouseMapper;
    
    @Override
    public List<Warehouse> findAvailableWarehouses(String sellerId, String rawMaterialName) {
        List<WarehouseJpaEntity> jpaEntities = warehouseJpaRepository.findBySellerIdAndRawMaterialName(sellerId, rawMaterialName);
        return jpaEntities.stream()
                .map(warehouseMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Warehouse> findById(UUID warehouseId) {
        return warehouseJpaRepository.findById(warehouseId)
                .map(warehouseMapper::toDomain);
    }
    
    @Override
    public Optional<Warehouse> findByWarehouseNumber(String warehouseNumber) {
        return warehouseJpaRepository.findByWarehouseNumber(warehouseNumber)
                .map(warehouseMapper::toDomain);
    }
    
    @Override
    public void save(Warehouse warehouse) {
        WarehouseJpaEntity jpaEntity = warehouseMapper.toJpaEntity(warehouse);
        warehouseJpaRepository.save(jpaEntity);
    }
}