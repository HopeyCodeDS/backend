package be.kdg.prog6.landsideContext.domain;

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

    // Method to scan the truck's license plate and register weight
    public void scanTruckAndRegisterWeight(String licensePlate, double weight) {
        this.scannedLicensePlate = licensePlate;
        this.truckWeight = weight;
        this.weighingTime = LocalDateTime.now();
        this.assignedWarehouseNumber = assignWarehouseNumber(); // Dynamically assign warehouse
    }

    // Method to assign warehouse number after weighing
    private String assignWarehouseNumber() {
        // Simple logic for warehouse assignment
        return "Warehouse-" + (int)(Math.random() * 10 + 1); // Random warehouse number between 1 and 10
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
