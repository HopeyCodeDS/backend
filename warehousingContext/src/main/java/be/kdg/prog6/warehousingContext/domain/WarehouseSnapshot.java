package be.kdg.prog6.warehousingContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record WarehouseSnapshot(
    UUID warehouseId,
    String warehouseNumber,
    UUID sellerId,
    String assignedMaterial,
    double maxCapacity,
    double currentCapacity,
    long version,
    LocalDateTime timestamp
) {
    public WarehouseSnapshot(Warehouse warehouse) {
        this(
            warehouse.getWarehouseId(),
            warehouse.getWarehouseNumber(),
            warehouse.getSellerId(),
            warehouse.getAssignedMaterial().getName(),
            warehouse.getMaxCapacity(),
            warehouse.getCurrentCapacity(),
            warehouse.getVersion(),
            LocalDateTime.now()
        );
    }
}
