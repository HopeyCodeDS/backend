package be.kdg.prog6.warehousingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(catalog = "warehousing", name = "warehouse_assignments")
public class WarehouseAssignmentJpaEntity {
    
    @Id
    @Column(name = "assignment_id")
    private UUID assignmentId;
    
    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;
    
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;
    
    @Column(name = "warehouse_number", nullable = false)
    private String warehouseNumber;
    
    @Column(name = "raw_material_name", nullable = false)
    private String rawMaterialName;
    
    @Column(name = "seller_id", nullable = false)
    private UUID sellerId;
    
    @Column(name = "truck_weight", nullable = false)
    private double truckWeight;
    
    @Column(name = "assignment_time", nullable = false)
    private LocalDateTime assignedAt;

} 