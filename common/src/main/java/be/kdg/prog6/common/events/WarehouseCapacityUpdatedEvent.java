package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class WarehouseCapacityUpdatedEvent {
    private final String warehouseId;
    private final double currentLoad;
    private final double capacity;
    private final boolean isOverCapacity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    private final LocalDateTime updatedAt;

    public WarehouseCapacityUpdatedEvent(String warehouseId, double currentLoad, double capacity, boolean isOverCapacity, LocalDateTime updatedAt) {
        this.warehouseId = warehouseId;
        this.currentLoad = currentLoad;
        this.capacity = capacity;
        this.isOverCapacity = isOverCapacity;
        this.updatedAt = updatedAt;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public double getCurrentLoad() {
        return currentLoad;
    }

    public double getCapacity() {
        return capacity;
    }

    public boolean isOverCapacity() {
        return isOverCapacity;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
