package be.kdg.prog6.watersideContext.adapters.out.db;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.InspectionOperation;
import be.kdg.prog6.watersideContext.domain.BunkeringOperation;
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
        
        // Inspection Operation
        entity.setInspectionPlannedDate(domain.getInspectionOperation().getPlannedDate());
        entity.setInspectionCompletedDate(domain.getInspectionOperation().getCompletedDate());
        entity.setInspectorSignature(domain.getInspectionOperation().getInspectorSignature());
        entity.setInspectionStatus(domain.getInspectionOperation().getStatus());
        
        // Bunkering Operation
        entity.setBunkeringPlannedDate(domain.getBunkeringOperation().getPlannedDate());
        entity.setBunkeringCompletedDate(domain.getBunkeringOperation().getCompletedDate());
        entity.setBunkeringStatus(domain.getBunkeringOperation().getStatus());
        
        return entity;
    }
    
    public ShippingOrder toDomain(ShippingOrderJpaEntity entity) {
        // This would be used for retrieving shipping orders
        // For now, we'll focus on the submission flow
        throw new UnsupportedOperationException("Domain mapping not implemented yet");
    }
}