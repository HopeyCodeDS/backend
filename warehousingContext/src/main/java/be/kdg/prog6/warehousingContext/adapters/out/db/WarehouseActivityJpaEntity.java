package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.WarehouseActivityAction;
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
@Table(catalog = "warehousing", name = "warehouse_activities")
public class WarehouseActivityJpaEntity {
    
    @Id
    @Column(name = "activity_id")
    private UUID activityId;
    
    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;
    
    @Column(name = "amount", nullable = false)
    private double amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private WarehouseActivityAction action;
    
    @Column(name = "point_in_time", nullable = false)
    private LocalDateTime pointInTime;
    
    @Column(name = "material_type", nullable = false)
    private String materialType;
    
    @Column(name = "license_plate")
    private String licensePlate;
    
    @Column(name = "description")
    private String description;
} 