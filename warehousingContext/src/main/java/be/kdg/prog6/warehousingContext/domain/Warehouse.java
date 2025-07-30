package be.kdg.prog6.warehousingContext.domain;

import lombok.Getter;
import java.util.UUID;

@Getter
public class Warehouse {
    private final UUID warehouseId;
    private final String warehouseNumber;
    private final String sellerId;
    private final RawMaterial assignedMaterial;
    private final double maxCapacity; // 500 kt = 500,000 tons
    private double currentCapacity;
    
    public Warehouse(UUID warehouseId, String warehouseNumber, String sellerId, RawMaterial assignedMaterial) {
        this.warehouseId = warehouseId;
        this.warehouseNumber = warehouseNumber;
        this.sellerId = sellerId;
        this.assignedMaterial = assignedMaterial;
        this.maxCapacity = 500_000.0; // 500 kt
        this.currentCapacity = 0.0;
    }
    
    public boolean canAcceptMaterial(RawMaterial material) {
        // If warehouse is empty, it can accept any material
        if (currentCapacity == 0) {
            return true;
        }
        // If warehouse has material, it can only accept the same type
        return assignedMaterial.getName().equals(material.getName());
    }
    
    public boolean hasAvailableCapacity(double amount) {
        return (currentCapacity + amount) <= maxCapacity;
    }
    
    public double getUtilizationPercentage() {
        return (currentCapacity / maxCapacity) * 100;
    }
    
    public boolean isAcceptingNewDeliveries() {
        // Don't accept new deliveries if warehouse is > 80% full
        return getUtilizationPercentage() <= 80.0;
    }
    
    public boolean canAcceptOverflow(double amount) {
        // Can accept overflow up to 110% capacity for already scheduled deliveries
        return (currentCapacity + amount) <= (maxCapacity * 1.1);
    }
    
    public void addCapacity(double amount) {
        if (currentCapacity + amount > maxCapacity * 1.1) {
            throw new IllegalStateException("Warehouse would exceed maximum overflow capacity");
        }
        this.currentCapacity += amount;
    }
} 