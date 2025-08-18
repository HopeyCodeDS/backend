package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.StorageFee;
import be.kdg.prog6.invoicingContext.ports.out.StorageFeeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public List<StorageFee> findByCalculationDateBetween(LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findByCalculationDateBetween(startDate, endDate)
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
    public boolean existsByCalculationDate(LocalDate calculationDate) {
        return jpaRepository.existsByCalculationDate(calculationDate);
    }
    
    @Override
    public Optional<StorageFee> findTopByOrderByCalculationDateDesc() {
        return jpaRepository.findTopByOrderByCalculationDateDesc()
            .stream()
            .map(mapper::toDomain)
            .findFirst();
    }
    
    @Override
    public List<StorageFee> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCalculationDateAndWarehouseAndMaterial(
        LocalDate calculationDate, String warehouseNumber, String materialType) {
        return jpaRepository.existsByCalculationDateAndWarehouseNumberAndMaterialType(
                calculationDate, warehouseNumber, materialType);
    }

    @Override
    public List<StorageFee> findBySellerId(UUID sellerId) {
        return jpaRepository.findBySellerId(sellerId)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<StorageFee> findBySellerIdAndCalculationDateBetween(UUID sellerId, LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findBySellerIdAndCalculationDateBetween(sellerId, startDate, endDate)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<StorageFee> findBySellerIdAndCalculationDate(UUID sellerId, LocalDate calculationDate) {
        return jpaRepository.findBySellerIdAndCalculationDate(sellerId, calculationDate)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
} 