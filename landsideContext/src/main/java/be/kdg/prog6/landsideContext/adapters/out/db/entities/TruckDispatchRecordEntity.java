package be.kdg.prog6.landsideContext.adapters.out.db.entities;



import be.kdg.prog6.common.domain.MaterialType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "truck_dispatch_records")
@Getter
@Setter
@NoArgsConstructor
public class TruckDispatchRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;

    @Enumerated(EnumType.STRING)
    private MaterialType materialType;

    private String conveyorBeltNumber;
    private String weighingBridgeNumber;
    private double weight;
    private LocalDateTime dispatchTime;
    private String warehouseId;

    public TruckDispatchRecordEntity(String licensePlate, MaterialType materialType, String conveyorBeltNumber, String weighingBridgeNumber, double weight, LocalDateTime dispatchTime, String warehouseId) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.conveyorBeltNumber = conveyorBeltNumber;
        this.weighingBridgeNumber = weighingBridgeNumber;
        this.weight = weight;
        this.dispatchTime = dispatchTime;
        this.warehouseId = warehouseId;
    }
}

