package be.kdg.prog6.warehousingContext.domain;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.domain.StorageCapacity;

import java.util.UUID;

public class Warehouse {

    public WarehouseId warehouseId;
    private StorageCapacity capacity;
    private MaterialType rawMaterial;
    private double currentStock;

    public record WarehouseId(UUID uuid) {
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public double getCapacity() {
        return capacity.getCapacityInKiloTons();
    }

    public void setCapacity(StorageCapacity capacity) {
        this.capacity = capacity;
    }

    public MaterialType getRawMaterial() {
        return rawMaterial;
    }

    public double getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(double currentStock) {
        this.currentStock = currentStock;
    }

   public double getAvailableStorageCapacity(){
        return this.capacity.getCapacityInKiloTons();
   }

   public boolean isFull(){
        return getCurrentStock() == getCapacity();
   }
   public boolean isEmpty(){
        return getCurrentStock() == 0;
   }
   public boolean notFull(){
        return getCurrentStock() == 0.8 * getCapacity() || !isFull();
   }
   public boolean isOverflow(){
        return getCurrentStock() >= getCapacity();
   }


    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouseId=" + warehouseId +
                ", capacity=" + capacity +
                ", rawMaterial=" + rawMaterial +
                ", currentStock=" + currentStock +
                '}';
    }
}
