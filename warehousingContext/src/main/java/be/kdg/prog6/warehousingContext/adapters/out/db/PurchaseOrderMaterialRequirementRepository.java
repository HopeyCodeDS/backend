package be.kdg.prog6.warehousingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseOrderMaterialRequirementRepository extends JpaRepository<PurchaseOrderMaterialRequirementJpaEntity, String> {
    
    List<PurchaseOrderMaterialRequirementJpaEntity> findByPurchaseOrderNumber(String purchaseOrderNumber);
    
    List<PurchaseOrderMaterialRequirementJpaEntity> findByPurchaseOrderNumberAndRawMaterialName(String purchaseOrderNumber, String rawMaterialName);
} 