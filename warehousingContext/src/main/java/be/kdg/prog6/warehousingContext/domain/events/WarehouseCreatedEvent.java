package be.kdg.prog6.warehousingContext.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record WarehouseCreatedEvent(
    UUID warehouseId,
    String warehouseNumber,
    UUID sellerId,
    String assignedMaterial,
    double maxCapacity,
    LocalDateTime timestamp
) {
    public WarehouseCreatedEvent(UUID warehouseId, String warehouseNumber, UUID sellerId, String assignedMaterial, double maxCapacity) {
        this(warehouseId, warehouseNumber, sellerId, assignedMaterial, maxCapacity, LocalDateTime.now());
    }
    
}
