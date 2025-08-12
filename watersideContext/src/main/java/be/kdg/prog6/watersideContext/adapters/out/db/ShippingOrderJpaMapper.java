package be.kdg.prog6.watersideContext.adapters.out.db;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class ShippingOrderJpaMapper {
    
    public ShippingOrderJpaEntity toJpaEntity(ShippingOrder domain) {
        ShippingOrderJpaEntity entity = new ShippingOrderJpaEntity();
        entity.setShippingOrderId(domain.getShippingOrderId().toString());
        entity.setShippingOrderNumber(domain.getShippingOrderNumber());
        entity.setPurchaseOrderReference(domain.getPurchaseOrderReference());
        entity.setVesselNumber(domain.getVesselNumber());
        entity.setCustomerNumber(domain.getCustomerNumber());
        entity.setEstimatedArrivalDate(domain.getEstimatedArrivalDate());
        entity.setEstimatedDepartureDate(domain.getEstimatedDepartureDate());
        entity.setActualArrivalDate(domain.getActualArrivalDate());
        entity.setActualDepartureDate(domain.getActualDepartureDate());
        entity.setStatus(domain.getStatus());
        
        // Foreman Validation fields
        entity.setForemanSignature(domain.getForemanSignature());
        entity.setValidationDate(domain.getValidationDate());
        
        // Inspection Operation
        entity.setInspectionPlannedDate(domain.getInspectionOperation().getPlannedDate());
        entity.setInspectionCompletedDate(domain.getInspectionOperation().getCompletedDate());
        entity.setInspectorSignature(domain.getInspectionOperation().getInspectorSignature());
        entity.setInspectionStatus(domain.getInspectionOperation().getStatus());
        
        // Bunkering Operation
        entity.setBunkeringPlannedDate(domain.getBunkeringOperation().getPlannedDate());
        entity.setBunkeringCompletedDate(domain.getBunkeringOperation().getCompletedDate());
        entity.setBunkeringOfficerSignature(domain.getBunkeringOperation().getBunkeringOfficerSignature());
        entity.setBunkeringStatus(domain.getBunkeringOperation().getStatus());
        
        return entity;
    }
    
    public ShippingOrder toDomain(ShippingOrderJpaEntity entity) {
        ShippingOrder domain = new ShippingOrder(
            UUID.fromString(entity.getShippingOrderId()),
            entity.getShippingOrderNumber(),
            entity.getPurchaseOrderReference(),
            entity.getVesselNumber(),
            entity.getCustomerNumber(),
            entity.getEstimatedArrivalDate(),
            entity.getEstimatedDepartureDate(),
            entity.getActualArrivalDate()
        );
        
        // Setting foreman validation data if exists (before setting status)
        if (entity.getForemanSignature() != null && entity.getValidationDate() != null) {
            domain.setForemanSignature(entity.getForemanSignature());
            domain.setValidationDate(entity.getValidationDate());
        }
        
        // Setting the status from database (after foreman data)
        domain.setStatus(entity.getStatus());
        
        // Setting actual dates 
        if (entity.getActualArrivalDate() != null) {
            domain.setActualArrivalDate(entity.getActualArrivalDate());
        }
        
        if (entity.getActualDepartureDate() != null) {
            domain.setActualDepartureDate(entity.getActualDepartureDate());
        }
        
        // Setting operation data
        if (entity.getInspectionCompletedDate() != null) {
            domain.getInspectionOperation().completeInspection(entity.getInspectorSignature());
        }
        
        if (entity.getBunkeringCompletedDate() != null) {
            domain.getBunkeringOperation().completeBunkering(entity.getBunkeringOfficerSignature());
        }
        
        return domain;
    }
}