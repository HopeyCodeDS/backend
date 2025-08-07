package be.kdg.prog6.invoicingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "invoicing", name = "storage_tracking")
@Getter
@Setter
public class StorageTrackingJpaEntity {
    
    @Id
    @Column(name = "tracking_id")
    private UUID trackingId;
    
    @Column(name = "warehouse_number", nullable = false)
    private String warehouseNumber;
    
    @Column(name = "customer_number", nullable = false)
    private String customerNumber;
    
    @Column(name = "material_type", nullable = false)
    private String materialType;
    
    @Column(name = "tons_stored", nullable = false)
    private double tonsStored;
    
    @Column(name = "remaining_tons", nullable = false)
    private double remainingTons;
    
    @Column(name = "delivery_time", nullable = false)
    private LocalDateTime deliveryTime;
    
    @Column(name = "pdt_id", nullable = false)
    private UUID pdtId;
} 