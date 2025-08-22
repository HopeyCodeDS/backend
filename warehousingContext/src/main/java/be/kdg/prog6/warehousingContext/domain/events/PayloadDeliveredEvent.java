package be.kdg.prog6.warehousingContext.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record PayloadDeliveredEvent(
    UUID warehouseId,
    double amount,
    String materialType,
    String licensePlate,
    double newCapacity,
    LocalDateTime timestamp
) {
    public PayloadDeliveredEvent(UUID warehouseId, double amount, String materialType, String licensePlate, double newCapacity) {
        this(warehouseId, amount, materialType, licensePlate, newCapacity, LocalDateTime.now());
    }
}