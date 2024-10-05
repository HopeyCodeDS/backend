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

    public Truck(LicensePlate licensePlate, double tareWeight, double grossWeight, String warehouseID, LocalDateTime arrivalWindow, MaterialType materialType) {
        this.licensePlate = licensePlate;
        this.tareWeight = tareWeight;
        this.grossWeight = grossWeight;
        this.warehouseID = warehouseID;
        this.arrivalWindow = arrivalWindow;
        this.materialType = materialType;
    }

    public Truck(String licensePlate, MaterialType material) {
        this.licensePlate = new LicensePlate(licensePlate);
        this.materialType = material;
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
}

