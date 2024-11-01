package be.kdg.prog6.landsideContext.domain.events;

import java.time.LocalDateTime;

public record WarehouseAssignedEvent(String licensePlate, String warehouseNumber, LocalDateTime timestamp) {
}
