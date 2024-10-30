package be.kdg.prog6.common.events;

import java.time.LocalDateTime;

public class TruckDockedEvent {
    private final String licensePlate;
    private final String warehouseId;
    private final LocalDateTime timestamp;

    public TruckDockedEvent(String licensePlate, String warehouseId) {
        this.licensePlate = licensePlate;
        this.warehouseId = warehouseId;
        this.timestamp = LocalDateTime.now();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
