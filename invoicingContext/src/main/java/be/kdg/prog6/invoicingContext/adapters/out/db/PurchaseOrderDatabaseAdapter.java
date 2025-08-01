package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.ports.out.PurchaseOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchaseOrderDatabaseAdapter implements PurchaseOrderRepositoryPort {
    
    private final PurchaseOrderJpaRepository purchaseOrderJpaRepository;
    private final PurchaseOrderJpaMapper purchaseOrderJpaMapper;
    
    @Override
    public void save(PurchaseOrder purchaseOrder) {
        PurchaseOrderJpaEntity jpaEntity = purchaseOrderJpaMapper.toJpaEntity(purchaseOrder);
        purchaseOrderJpaRepository.save(jpaEntity);
    }
    
    @Override
    public Optional<PurchaseOrder> findById(UUID purchaseOrderId) {
        return purchaseOrderJpaRepository.findById(purchaseOrderId)
                .map(purchaseOrderJpaMapper::toDomain);
    }
    
    @Override
    public Optional<PurchaseOrder> findByPurchaseOrderNumber(String purchaseOrderNumber) {
        return purchaseOrderJpaRepository.findByPurchaseOrderNumber(purchaseOrderNumber)
                .map(purchaseOrderJpaMapper::toDomain);
    }
    
    @Override
    public List<PurchaseOrder> findByCustomerNumber(String customerNumber) {
        return purchaseOrderJpaRepository.findByCustomerNumber(customerNumber).stream()
                .map(purchaseOrderJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderJpaRepository.findAll().stream()
                .map(purchaseOrderJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
} 