package be.kdg.prog6.common.events;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TruckArrivalEvent {
    private final String licensePlate;
    private final String warehouseId;
    private final String materialType;
    private final LocalDateTime actualArrival;
    private final double load;

    public TruckArrivalEvent(String licensePlate, String warehouseId, String materialType, LocalDateTime actualArrival, double load) {
        this.licensePlate = licensePlate;
        this.warehouseId = warehouseId;
        this.materialType = materialType;
        this.actualArrival = actualArrival;
        this.load = load;
    }

    // Getters
}
