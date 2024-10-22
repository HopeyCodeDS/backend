package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.MaterialType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class Truck {
    private final LicensePlate licensePlate;

    @Getter
    @Setter
    private String warehouseID;

    @Getter
    @Setter
    private LocalDateTime arrivalTime;

    @Getter
    private double weight;

    @Getter
    private final MaterialType materialType;

    @Getter
    @Setter
    private String currentWeighingBridgeNumber;

    @Getter
    private String assignedConveyorBelt;

    @Getter
    private boolean weighed;

    public Truck(String licensePlate, MaterialType materialType) {
        this.licensePlate = new LicensePlate(licensePlate);
        this.materialType = materialType;
    }

    public Truck(LicensePlate licensePlate, double weight) {
        this.licensePlate = licensePlate;
        this.weight = weight;
        this.materialType = null; // Or initialize properly
    }

    public String getLicensePlate() {
        return licensePlate.plateNumber();
    }

    public void assignConveyorBelt(String conveyorBelt) {
        this.assignedConveyorBelt = conveyorBelt;
    }

    public void markAsWeighed() {
        this.weighed = true;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "licensePlate=" + licensePlate +
                ", warehouseID='" + warehouseID + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", weight=" + weight +
                ", materialType=" + materialType +
                ", weighed=" + weighed +
                '}';
    }
}
