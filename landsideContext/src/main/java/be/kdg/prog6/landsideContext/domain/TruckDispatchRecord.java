package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.MaterialType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TruckDispatchRecord {
    private final String licensePlate;
    private final MaterialType materialType;
    private final String conveyorBeltNumber;
    private final String weighingBridgeNumber;
    private final double weight;
    private final LocalDateTime dispatchTime;

    public TruckDispatchRecord(String licensePlate, MaterialType materialType, String conveyorBeltNumber, String weighingBridgeNumber, double weight, LocalDateTime dispatchTime) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.conveyorBeltNumber = conveyorBeltNumber;
        this.weighingBridgeNumber = weighingBridgeNumber;
        this.weight = weight;
        this.dispatchTime = dispatchTime;
    }

    // Getters and toString
}
