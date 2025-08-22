package be.kdg.prog6.warehousingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(catalog = "warehousing", name = "payload_delivery_tickets")
public class PayloadDeliveryTicketJpaEntity {
    
    @Id
    @Column(name = "pdt_id", columnDefinition = "BINARY(16)")
    private UUID pdtId;
    
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;
    
    @Column(name = "raw_material_name", nullable = false)
    private String rawMaterialName;
    
    @Column(name = "warehouse_number", nullable = false)
    private String warehouseNumber;
    
    @Column(name = "conveyor_belt_number", nullable = false)
    private String conveyorBeltNumber;
    
    @Column(name = "payload_weight", nullable = false)
    private double payloadWeight;
    
    @Column(name = "seller_id", nullable = false)
    private UUID sellerId;
    
    @Column(name = "delivery_time", nullable = false)
    private LocalDateTime deliveryTime;

    @Column(name = "new_weighing_bridge_number", nullable = false)
    private String newWeighingBridgeNumber;
} 