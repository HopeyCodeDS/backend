package be.kdg.prog6.landsideContext.adapters.out.db.entities;

import be.kdg.prog6.common.domain.MaterialType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(catalog = "landside", name = "trucks")
public class TruckJpaEntity {

    @Id
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "warehouse_id")
    private String warehouseID;

    @Column
    private Double weight = 0.0;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Column(name = "weighing_bridge_number")
    private String weighingBridgeNumber;

    @Column(name = "assigned_conveyor_belt")
    private String assignedConveyorBelt;

    @Column(name = "weighed", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean weighed = false;

    @Column(name = "docked", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean docked = false;

    public TruckJpaEntity() {
//        this.weighed = false; // Ensure weighed is set to false by default
    }

}
