package be.kdg.prog6.watersideContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import be.kdg.prog6.watersideContext.domain.BunkeringOperation;
import be.kdg.prog6.watersideContext.domain.InspectionOperation;
import be.kdg.prog6.watersideContext.domain.InspectionOperation.InspectionStatus;
import be.kdg.prog6.watersideContext.domain.ShippingOrder.ShippingOrderStatus;

@Entity
@Table(catalog = "waterside", name = "shipping_orders")
@Data
public class ShippingOrderJpaEntity {
    @Id
    private String shippingOrderId;
    
    @Column(unique = true, nullable = false)
    private String shippingOrderNumber;
    
    @Column(nullable = false)
    private String purchaseOrderReference;
    
    @Column(nullable = false)
    private String vesselNumber;
    
    @Column(nullable = false)
    private String customerNumber;
    
    @Column(nullable = false)
    private LocalDateTime estimatedArrivalDate;
    
    @Column(nullable = false)
    private LocalDateTime estimatedDepartureDate;
    
    private LocalDateTime actualArrivalDate;
    private LocalDateTime actualDepartureDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingOrderStatus status;
    
    // Inspection Operation fields
    private LocalDateTime inspectionPlannedDate;
    private LocalDateTime inspectionCompletedDate;
    private String inspectorSignature;

    @Enumerated(EnumType.STRING)
    private InspectionOperation.InspectionStatus inspectionStatus;
    
    // Bunkering Operation fields
    private LocalDateTime bunkeringPlannedDate;
    private LocalDateTime bunkeringCompletedDate;
    private String bunkeringOfficerSignature;
    
    @Enumerated(EnumType.STRING)
    private BunkeringOperation.BunkeringStatus bunkeringStatus;
}