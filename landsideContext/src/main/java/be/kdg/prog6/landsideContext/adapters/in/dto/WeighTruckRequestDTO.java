package be.kdg.prog6.landsideContext.adapters.in.dto;

public class WeighTruckRequestDTO {

    private String licensePlate;
    private double grossWeight;
    private double tareWeight;

    // Getters and Setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(double tareWeight) {
        this.tareWeight = tareWeight;
    }
}
