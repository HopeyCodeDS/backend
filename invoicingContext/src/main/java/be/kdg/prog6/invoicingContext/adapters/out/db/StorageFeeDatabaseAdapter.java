package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.StorageFee;
import be.kdg.prog6.invoicingContext.ports.out.StorageFeeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StorageFeeDatabaseAdapter implements StorageFeeRepositoryPort {
    
    private final StorageFeeJpaRepository jpaRepository;
    private final StorageFeeJpaMapper mapper;
    
    @Override
    public void save(StorageFee storageFee) {
        StorageFeeJpaEntity entity = mapper.toJpaEntity(storageFee);
        jpaRepository.save(entity);
    }
    
    @Override
    public List<StorageFee> findByCustomerNumber(String customerNumber) {
        return jpaRepository.findByCustomerNumber(customerNumber)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<StorageFee> findByCalculationDate(LocalDate calculationDate) {
        return jpaRepository.findByCalculationDate(calculationDate)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<StorageFee> findByWarehouseNumber(String warehouseNumber) {
        return jpaRepository.findByWarehouseNumber(warehouseNumber)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<StorageFee> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
} 