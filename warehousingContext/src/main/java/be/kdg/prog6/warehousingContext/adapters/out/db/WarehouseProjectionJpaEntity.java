package be.kdg.prog6.warehousingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(catalog = "warehousing", name = "warehouse_projections")
@Data
public class WarehouseProjectionJpaEntity {
    @Id
    private UUID warehouseId;
    
    @Column(name = "warehouse_number")
    private String warehouseNumber;
    
    @Column(name = "seller_id")
    private String sellerId;
    
    @Column(name = "assigned_material")
    private String assignedMaterial;
    
    @Column(name = "max_capacity")
    private double maxCapacity;
    
    @Column(name = "current_capacity")
    private double currentCapacity;
} 