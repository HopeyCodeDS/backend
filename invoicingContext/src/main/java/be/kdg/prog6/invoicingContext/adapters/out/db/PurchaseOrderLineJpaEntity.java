package be.kdg.prog6.invoicingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(catalog = "invoicing", name = "purchase_order_lines")
public class PurchaseOrderLineJpaEntity {
    
    @Id
    @Column(name = "line_id")
    private UUID lineId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrderJpaEntity purchaseOrder;
    
    @Column(name = "raw_material_name", nullable = false)
    private String rawMaterialName;
    
    @Column(name = "amount_in_tons", nullable = false)
    private double amountInTons;
    
    @Column(name = "price_per_ton", nullable = false)
    private double pricePerTon;
} 