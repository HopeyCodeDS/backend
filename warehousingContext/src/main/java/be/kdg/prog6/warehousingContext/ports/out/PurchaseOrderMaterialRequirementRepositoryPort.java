package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderMaterialRequirement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseOrderMaterialRequirementRepositoryPort {
    PurchaseOrderMaterialRequirement save(PurchaseOrderMaterialRequirement requirement);
    List<PurchaseOrderMaterialRequirement> findByPurchaseOrderNumber(String purchaseOrderNumber);
    List<PurchaseOrderMaterialRequirement> findByPurchaseOrderNumberAndRawMaterialName(String purchaseOrderNumber, String rawMaterialName);
    Optional<PurchaseOrderMaterialRequirement> findById(UUID requirementId);
} 