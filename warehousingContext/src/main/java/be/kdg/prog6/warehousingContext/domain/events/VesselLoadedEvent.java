package be.kdg.prog6.warehousingContext.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record VesselLoadedEvent(
    UUID warehouseId,
    double amount,
    String materialType,
    double newCapacity,
    LocalDateTime timestamp
) {
    public VesselLoadedEvent(UUID warehouseId, double amount, String materialType, double newCapacity) {
        this(warehouseId, amount, materialType, newCapacity, LocalDateTime.now());
    }
}
