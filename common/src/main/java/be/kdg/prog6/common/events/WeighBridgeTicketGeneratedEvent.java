package be.kdg.prog6.common.events;


import java.time.LocalDateTime;

public class WeighBridgeTicketGeneratedEvent {
    private final String licensePlate;
    private final double grossWeight;
    private final double tareWeight;
    private final double netWeight;
    private final LocalDateTime timestamp;

    public WeighBridgeTicketGeneratedEvent(String licensePlate, double grossWeight, double tareWeight, double netWeight, LocalDateTime timestamp) {
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = netWeight;
        this.timestamp = timestamp;
    }

    // Getters
    public String getLicensePlate() { return licensePlate; }
    public double getGrossWeight() { return grossWeight; }
    public double getTareWeight() { return tareWeight; }
    public double getNetWeight() { return netWeight; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
