package be.kdg.prog6.warehousingContext.adapters.out.db;

import java.util.UUID;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderFulfillmentTrackingJpaMapper {
    
    public PurchaseOrderFulfillmentTrackingJpaEntity toJpaEntity(PurchaseOrderFulfillmentTracking domain) {
        PurchaseOrderFulfillmentTrackingJpaEntity entity = new PurchaseOrderFulfillmentTrackingJpaEntity();
        entity.setTrackingId(domain.getTrackingId().toString());
        entity.setPurchaseOrderNumber(domain.getPurchaseOrderNumber());
        entity.setCustomerNumber(domain.getCustomerNumber());
        entity.setCustomerName(domain.getCustomerName());
        entity.setOrderDate(domain.getOrderDate());
        entity.setTotalValue(domain.getTotalValue());
        entity.setStatus(domain.getStatus());
        entity.setFulfillmentDate(domain.getFulfillmentDate());
        entity.setVesselNumber(domain.getVesselNumber());
        return entity;
    }
    
    public PurchaseOrderFulfillmentTracking toDomain(PurchaseOrderFulfillmentTrackingJpaEntity entity) {
        PurchaseOrderFulfillmentTracking domain = new PurchaseOrderFulfillmentTracking(
            UUID.fromString(entity.getTrackingId()),
            entity.getPurchaseOrderNumber(),
            entity.getCustomerNumber(),
            entity.getCustomerName(),
            entity.getTotalValue(),
            entity.getOrderDate(),
            entity.getStatus(),
            entity.getFulfillmentDate(),
            entity.getVesselNumber()
        );
        
        if (PurchaseOrderFulfillmentTracking.FulfillmentStatus.FULFILLED.equals(entity.getStatus())) {
            domain.markAsFulfilled(entity.getVesselNumber());
        }
        
        return domain;
    }
} 