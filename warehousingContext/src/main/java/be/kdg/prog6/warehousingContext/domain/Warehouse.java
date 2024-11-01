package be.kdg.prog6.warehousingContext.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Warehouse {
    private String warehouseId;
    private String customerId;
    private String materialType;
    private double capacity; // Max capacity in kt
    private double currentLoad; // Current load in kt

    public Warehouse(String warehouseId, String customerId, String materialType, double capacity) {
        this.warehouseId = warehouseId;
        this.customerId = customerId;
        this.materialType = materialType;
        this.capacity = capacity;
        this.currentLoad = 0;
    }

    public Warehouse() {
    }

    public boolean addLoad(double load) {
        this.currentLoad += load;
        return this.currentLoad <= (this.capacity * 1.15); // Allows for 115% overflow
    }

    public double getOverflowPercentage() {
        return (currentLoad / capacity) * 100;
    }
}
