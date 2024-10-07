package be.kdg.prog6.landsideContext.domain;

import java.time.LocalDateTime;

public class WeighBridgeTicket {
    private String licensePlate;
    private double grossWeight;
    private double tareWeight;
    private double netWeight;
    private LocalDateTime timestamp;

    // Constructor
    public WeighBridgeTicket(String licensePlate, double grossWeight, double tareWeight) {
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = grossWeight - tareWeight;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getLicensePlate() {
        return licensePlate;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public double getTareWeight() {
        return tareWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Weighbridge Ticket: \n" +
                "License Plate: " + licensePlate + "\n" +
                "Gross Weight: " + grossWeight + "\n" +
                "Tare Weight: " + tareWeight + "\n" +
                "Net Weight: " + netWeight + "\n" +
                "Timestamp: " + timestamp;
    }
}
