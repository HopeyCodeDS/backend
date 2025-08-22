package be.kdg.prog6.warehousingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(catalog = "warehousing", name = "purchase_order_material_requirements")
@Getter
@Setter
public class PurchaseOrderMaterialRequirementJpaEntity {
    
    @Id
    @Column(name = "requirement_id", columnDefinition = "BINARY(16)")
    private UUID requirementId;
    
    @Column(name = "purchase_order_number", nullable = false)
    private String purchaseOrderNumber;
    
    @Column(name = "raw_material_name", nullable = false)
    private String rawMaterialName;
    
    @Column(name = "required_amount_in_tons", nullable = false)
    private double requiredAmountInTons;
    
    @Column(name = "price_per_ton", nullable = false)
    private double pricePerTon;
    
    @Column(name = "total_value", nullable = false)
    private double totalValue;
    
    @Column(name = "fulfilled_amount_in_tons", nullable = false)
    private double fulfilledAmountInTons;
    
    @Column(name = "remaining_amount_in_tons", nullable = false)
    private double remainingAmountInTons;
}  