package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(catalog = "invoicing", name = "purchase_orders")
public class PurchaseOrderJpaEntity {
    
    @Id
    @Column(name = "purchase_order_id")
    private UUID purchaseOrderId;
    
    @Column(name = "purchase_order_number", nullable = false, unique = true)
    private String purchaseOrderNumber;
    
    @Column(name = "customer_number", nullable = false)
    private String customerNumber;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseOrder.PurchaseOrderStatus status;
    
    @Column(name = "total_value", nullable = false)
    private double totalValue;
    
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PurchaseOrderLineJpaEntity> orderLines = new ArrayList<>();
} 