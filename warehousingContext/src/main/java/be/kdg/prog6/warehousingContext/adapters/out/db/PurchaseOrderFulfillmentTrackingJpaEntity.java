package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking.FulfillmentStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "warehousing", name = "purchase_order_fulfillment_tracking")
@Data
public class PurchaseOrderFulfillmentTrackingJpaEntity {
    
    @Id
    @Column(name = "tracking_id", columnDefinition = "BINARY(16)")
    private UUID trackingId;
    
    @Column(name = "purchase_order_number", nullable = false, unique = true)
    private String purchaseOrderNumber; // Reference to PO in InvoicingContext
    
    @Column(name = "customer_number", nullable = false)
    private String customerNumber;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
    
    @Column(name = "total_value", nullable = false)
    private double totalValue;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FulfillmentStatus status; // "OUTSTANDING" or "FULFILLED"
    
    @Column(name = "fulfillment_date")
    private LocalDateTime fulfillmentDate;
    
    @Column(name = "vessel_number")
    private String vesselNumber;
} 