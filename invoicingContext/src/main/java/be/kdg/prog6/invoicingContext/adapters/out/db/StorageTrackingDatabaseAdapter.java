package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.StorageTracking;
import be.kdg.prog6.invoicingContext.ports.out.StorageTrackingRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StorageTrackingDatabaseAdapter implements StorageTrackingRepositoryPort {
    
    private final StorageTrackingJpaRepository jpaRepository;
    private final StorageTrackingJpaMapper mapper;
    
    @Override
    public void save(StorageTracking storageTracking) {
        StorageTrackingJpaEntity entity = mapper.toJpaEntity(storageTracking);
        jpaRepository.save(entity);
    }
    
    @Override
    public List<StorageTracking> findByWarehouseAndMaterialOrderByDeliveryTime(String warehouseNumber, String materialType) {
        return jpaRepository.findByWarehouseNumberAndMaterialTypeOrderByDeliveryTimeAsc(warehouseNumber, materialType)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<StorageTracking> findByCustomerNumber(String customerNumber) {
        return jpaRepository.findByCustomerNumber(customerNumber)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<StorageTracking> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public Map<String, List<StorageTracking>> findAllGroupedByWarehouseAndMaterial() {
        List<StorageTrackingJpaEntity> entities = jpaRepository.findAllOrderedByWarehouseAndMaterial();
        return mapper.groupByWarehouseAndMaterial(entities);
    }

    @Override
    public boolean existsByPdtId(UUID pdtId) {
        return jpaRepository.existsByPdtId(pdtId);
    }
} 