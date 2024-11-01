package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/*
* This event class represents the warehouse load status update.
* */
public class WarehouseLoadStatusEvent {
    private final String warehouseId;
    private final double currentLoadPercentage;

    @JsonCreator
    public WarehouseLoadStatusEvent(
            @JsonProperty("warehouseId") String warehouseId,
            @JsonProperty("currentLoadPercentage") double currentLoadPercentage) {
        this.warehouseId = warehouseId;
        this.currentLoadPercentage = currentLoadPercentage;
    }

    public String getWarehouseId() { return warehouseId; }
    public double getCurrentLoadPercentage() { return currentLoadPercentage; }
}
