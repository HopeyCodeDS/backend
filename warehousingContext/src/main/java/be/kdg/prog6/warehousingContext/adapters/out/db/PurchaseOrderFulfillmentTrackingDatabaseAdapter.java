package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderFulfillmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderFulfillmentTrackingDatabaseAdapter implements PurchaseOrderFulfillmentRepositoryPort {
    
    private final PurchaseOrderFulfillmentTrackingJpaRepository jpaRepository;
    private final PurchaseOrderFulfillmentTrackingJpaMapper mapper;
    
    @Override
    public void save(PurchaseOrderFulfillmentTracking fulfillment) {
        PurchaseOrderFulfillmentTrackingJpaEntity entity = mapper.toJpaEntity(fulfillment);
        PurchaseOrderFulfillmentTrackingJpaEntity savedEntity = jpaRepository.save(entity);
        log.debug("Saved PO fulfillment tracking: {}", savedEntity.getTrackingId());
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
                .toList();
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

    @Override
    public Optional<PurchaseOrderFulfillmentTracking> findById(UUID trackingId) {
        return jpaRepository.findById(trackingId)
                .map(mapper::toDomain);
    }
} 