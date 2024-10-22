package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.MaterialType;

import java.time.LocalDateTime;

public class WeighingBridge {
    private String bridgeNumber;
    private String scannedLicensePlate;
    private double truckWeight;
    private LocalDateTime weighingTime;
    private String assignedWarehouseNumber;

    public WeighingBridge(String bridgeNumber) {
        this.bridgeNumber = bridgeNumber;
    }

    /// Method to scan the truck's license plate, register weight, and assign a warehouse based on material type
    public void scanTruckAndRegisterWeight(String licensePlate, double weight, MaterialType materialType) {
        this.scannedLicensePlate = licensePlate;
        this.truckWeight = weight;
        this.weighingTime = LocalDateTime.now();
        this.assignedWarehouseNumber = assignWarehouseNumber(materialType); // Dynamically assign warehouse
    }

    /// Method to assign warehouse number based on material type
    private String assignWarehouseNumber(MaterialType materialType) {
        // Simple logic for warehouse assignment
        switch (materialType) {
            case GYPSUM:
                return "Warehouse-1"; // For Gypsum
            case IRON_ORE:
                return "Warehouse-2"; // For Iron Ore
            case CEMENT:
                return "Warehouse-3"; // For Cement
            case PETCOKE:
                return "Warehouse-4"; // For Petcoke
            case SLAG:
                return "Warehouse-5"; // For Slag
            default:
                return "Check another warehouse or speak to the supplier"; // For other materials
        }
    }

    public String getBridgeNumber() {
        return bridgeNumber;
    }

    public String getScannedLicensePlate() {
        return scannedLicensePlate;
    }

    public double getTruckWeight() {
        return truckWeight;
    }

    public LocalDateTime getWeighingTime() {
        return weighingTime;
    }

    public String getAssignedWarehouseNumber() {
        return assignedWarehouseNumber;
    }
}
