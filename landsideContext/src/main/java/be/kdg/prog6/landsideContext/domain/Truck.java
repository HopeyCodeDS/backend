package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.MaterialType;

import java.time.LocalDateTime;

public class Truck {
    private LicensePlate licensePlate;
    private String warehouseID;
    private LocalDateTime arrivalWindow;
    private MaterialType materialType;
    private String currentWeighingBridgeNumber; // The weighing bridge number linked to this truck
    private String assignedConveyorBelt;
    private boolean weighed;

    public Truck(String licensePlate, LocalDateTime arrivalWindow, MaterialType material) {
        this.licensePlate = new LicensePlate(licensePlate);
        this.arrivalWindow = arrivalWindow;
        this.materialType = material;
    }

    public Truck(LicensePlate licensePlate, String warehouseID, LocalDateTime arrivalWindow, MaterialType materialType, boolean weighed) {
        this.licensePlate = licensePlate;
        this.warehouseID = warehouseID;
        this.arrivalWindow = arrivalWindow;
        this.materialType = materialType;
        this.currentWeighingBridgeNumber = null;
        this.assignedConveyorBelt = null;
        this.weighed = false;
    }

    public String getLicensePlate() {
        return licensePlate.plateNumber();
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public LocalDateTime getArrivalWindow() {
        return arrivalWindow;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public String getCurrentWeighingBridgeNumber() {
        return currentWeighingBridgeNumber;
    }

    public String getAssignedConveyorBelt() {
        return assignedConveyorBelt;
    }

    public void setCurrentWeighingBridgeNumber(String currentWeighingBridgeNumber) {
        this.currentWeighingBridgeNumber = currentWeighingBridgeNumber;
    }

    public void setAssignedConveyorBelt(String assignedConveyorBelt) {
        this.assignedConveyorBelt = assignedConveyorBelt;
    }

    // Method to mark the truck as weighed
    public void markAsWeighed() {
        this.weighed = true;
    }

    public boolean isWeighed() {
        return weighed;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "licensePlate=" + licensePlate +
                ", warehouseID='" + warehouseID + '\'' +
                ", arrivalWindow=" + arrivalWindow +
                ", materialType=" + materialType +
                ", currentWeighingBridgeNumber='" + currentWeighingBridgeNumber + '\'' +
                ", assignedConveyorBelt='" + assignedConveyorBelt + '\'' +
                ", weighed=" + weighed +
                '}';
    }
}

