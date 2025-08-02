package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderFulfillmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchaseOrderFulfillmentDatabaseAdapter implements PurchaseOrderFulfillmentRepositoryPort {
    
    private final PurchaseOrderFulfillmentTrackingJpaRepository jpaRepository;
    private final PurchaseOrderFulfillmentTrackingJpaMapper mapper;
    
    @Override
    public void save(PurchaseOrderFulfillmentTracking fulfillment) {
        PurchaseOrderFulfillmentTrackingJpaEntity entity = mapper.toJpaEntity(fulfillment);
        jpaRepository.save(entity);
    }
    
    @Override
    public Optional<PurchaseOrderFulfillmentTracking> findByPurchaseOrderNumber(String purchaseOrderNumber) {
        return jpaRepository.findByPurchaseOrderNumber(purchaseOrderNumber)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<PurchaseOrderFulfillmentTracking> findByStatus(PurchaseOrderFulfillmentTracking.FulfillmentStatus status) {
        return jpaRepository.findByStatus(status)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PurchaseOrderFulfillmentTracking> findByCustomerNumber(String customerNumber) {
        return jpaRepository.findByCustomerNumber(customerNumber)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PurchaseOrderFulfillmentTracking> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
} 