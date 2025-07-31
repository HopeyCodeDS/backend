package be.kdg.prog6.landsideContext.domain.commands;

public record GenerateWeighbridgeTicketCommand(
    String licensePlate,
    double grossWeight,
    double tareWeight
) {
    public GenerateWeighbridgeTicketCommand {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate is required");
        }
        if (grossWeight <= 0) {
            throw new IllegalArgumentException("Gross weight must be greater than 0");
        }
        if (tareWeight <= 0) {
            throw new IllegalArgumentException("Tare weight must be greater than 0");
        }
        if (grossWeight <= tareWeight) {
            throw new IllegalArgumentException("Gross weight must be greater than tare weight");
        }
    }
} 