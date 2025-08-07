package be.kdg.prog6.landsideContext.domain.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record GenerateWeighbridgeTicketCommand(
    String licensePlate,
    double grossWeight,
    double tareWeight,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime weighingTime
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
        if (weighingTime == null) {
            throw new IllegalArgumentException("Weighing time is required");
        }
    }
} 