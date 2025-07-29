package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.TruckLocation;
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
@Table(catalog = "landside", name = "truck_movements")
public class TruckMovementJpaEntity {
    
    @Id
    @Column(name = "movement_id")
    private UUID movementId;
    
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;
    
    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "current_location", nullable = false)
    private TruckLocation currentLocation;
    
    @Column(name = "assigned_bridge_id")
    private UUID assignedBridgeId;
    
    @Column(name = "bridge_assignment_time")
    private LocalDateTime bridgeAssignmentTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_bridge_id", insertable = false, updatable = false)
    private WeighingBridgeJpaEntity assignedBridge;
} 