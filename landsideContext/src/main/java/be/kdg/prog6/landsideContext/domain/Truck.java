package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.MaterialType;

import java.time.LocalDateTime;

public class Truck {
    private LicensePlate licensePlate;
    private double tareWeight;
    private double grossWeight;
    private String warehouseID;
    private LocalDateTime arrivalWindow;
    private MaterialType materialType;
    private String currentWeighingBridgeNumber; // The weighing bridge number linked to this truck
    private String assignedConveyorBelt;
    private boolean weighed;

    public Truck(LicensePlate licensePlate, double tareWeight, double grossWeight, String warehouseID, LocalDateTime arrivalWindow, MaterialType materialType) {
        this.licensePlate = licensePlate;
        this.tareWeight = tareWeight;
        this.grossWeight = grossWeight;
        this.warehouseID = warehouseID;
        this.arrivalWindow = arrivalWindow;
        this.materialType = materialType;
    }

    public Truck(LicensePlate licensePlate,LocalDateTime arrivalWindow, MaterialType materialType,String warehouseID) {
        this.licensePlate = licensePlate;
        this.arrivalWindow = arrivalWindow;
        this.materialType = materialType;
        this.warehouseID = warehouseID;
        this.currentWeighingBridgeNumber = null;
        this.assignedConveyorBelt = null;

    }

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

    public double getTareWeight() {
        return tareWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
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

    public double calculateNetWeight() {
        return grossWeight - tareWeight;
    }

    public WeightBridgeTicket generateWeightBridgeTicket() {
        return null;
    }

    // Methods to assign weighing bridge and conveyor belt
    public void assignWeighingBridge(String weighingBridgeNumber) {
        this.currentWeighingBridgeNumber = weighingBridgeNumber;
    }

    public void assignConveyorBelt(String conveyorBelt) {
        this.assignedConveyorBelt = conveyorBelt;
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

}

