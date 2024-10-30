package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.MaterialType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Truck {

    private final LicensePlate licensePlate;

    @Setter
    private String warehouseID;

    @Setter
    private double weight;

    @Setter
    private MaterialType materialType;

    @Setter
    private LocalDateTime arrivalTime;

    @Setter
    private String weighingBridgeNumber;

    @Setter
    private String assignedConveyorBelt;

    @Setter
    private boolean weighed;
    private boolean docked = false;

    // Constructor for Truck with String licensePlate and MaterialType
    public Truck(String licensePlate, MaterialType materialType) {
        this.licensePlate = new LicensePlate(licensePlate);
        this.materialType = materialType;
    }

    // Constructor for Truck with LicensePlate and weight
    public Truck(LicensePlate licensePlate) {
        this.licensePlate = licensePlate;
        this.weight = getWeight();
        this.materialType = null; // Or initialize properly
    }


    public Truck(LicensePlate licensePlate, String warehouseID, double weight, MaterialType materialType, boolean weighed) {
        this.licensePlate = licensePlate;
        this.warehouseID = warehouseID;
        this.weight = weight;
        this.materialType = materialType;
        this.weighed = weighed;
    }

    public Truck(LicensePlate licensePlate, String warehouseID, double weight, MaterialType materialType, LocalDateTime arrivalTime, String weighingBridgeNumber, String assignedConveyorBelt, boolean weighed, boolean docked) {
        this.licensePlate = licensePlate;
        this.warehouseID = warehouseID;
        this.weight = weight;
        this.materialType = materialType;
        this.arrivalTime = arrivalTime;
        this.weighingBridgeNumber = weighingBridgeNumber;
        this.assignedConveyorBelt = assignedConveyorBelt;
        this.weighed = weighed;
        this.docked = docked;
    }

    public String getLicensePlate() {
        if (licensePlate == null) {
            throw new IllegalStateException("License plate is not set for this truck.");
        }
        return licensePlate.plateNumber();
    }
    public void assignWeighingBridge(String bridgeNumber) {
        if (bridgeNumber != null && !bridgeNumber.isEmpty()) {
            this.weighingBridgeNumber = bridgeNumber;
        }
    }
    public void assignWarehouse(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public boolean hasAssignedWeighingBridge() {
        return weighingBridgeNumber != null;
    }

    public void assignConveyorBelt(String conveyorBelt) {
        this.assignedConveyorBelt = conveyorBelt;
    }

    public void markAsWeighed() {
        this.weighed = true;
    }

    public void dock() {
        this.docked = true;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "licensePlate=" + licensePlate + '\'' +
                ", warehouseID='" + warehouseID + '\'' +
                ", weight=" + weight + '\'' +
                ", materialType=" + materialType + '\'' +
                ", arrivalTime=" + arrivalTime + '\'' +
                ", bridgeNumber=" + weighingBridgeNumber + '\'' +
                ", assignedConveyorBelt='" + assignedConveyorBelt + '\'' +
                ", weighed=" + weighed +
                '}';
    }
}
