package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderMaterialRequirement;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class PurchaseOrderMaterialRequirementJpaMapper {
    
    public PurchaseOrderMaterialRequirementJpaEntity toJpa(PurchaseOrderMaterialRequirement domain) {
        PurchaseOrderMaterialRequirementJpaEntity entity = new PurchaseOrderMaterialRequirementJpaEntity();
        entity.setRequirementId(domain.getRequirementId().toString());
        entity.setPurchaseOrderNumber(domain.getPurchaseOrderNumber());
        entity.setRawMaterialName(domain.getRawMaterialName());
        entity.setRequiredAmountInTons(domain.getRequiredAmountInTons());
        entity.setPricePerTon(domain.getPricePerTon());
        entity.setTotalValue(domain.getTotalValue());
        entity.setFulfilledAmountInTons(domain.getFulfilledAmountInTons());
        entity.setRemainingAmountInTons(domain.getRemainingAmountInTons());
        return entity;
    }
    
    public PurchaseOrderMaterialRequirement toDomain(PurchaseOrderMaterialRequirementJpaEntity entity) {
        return new PurchaseOrderMaterialRequirement(
            UUID.fromString(entity.getRequirementId()),
            entity.getPurchaseOrderNumber(),
            entity.getRawMaterialName(),
            entity.getRequiredAmountInTons(),
            entity.getPricePerTon(),
            entity.getFulfilledAmountInTons()
        );
    }
} 