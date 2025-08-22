package be.kdg.prog6.warehousingContext.domain;

import be.kdg.prog6.warehousingContext.domain.events.WarehouseCreatedEvent;
import be.kdg.prog6.warehousingContext.domain.events.PayloadDeliveredEvent;
import be.kdg.prog6.warehousingContext.domain.events.VesselLoadedEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Warehouse {
    private final UUID warehouseId;
    private final String warehouseNumber;
    private final UUID sellerId;
    private final RawMaterial assignedMaterial;
    private final double maxCapacity; // 500 kt = 500,000 tons
    private double currentCapacity;
    private final List<Object> domainEvents = new ArrayList<>();
    private long version = 0;
    
    public Warehouse(UUID warehouseId, String warehouseNumber, UUID sellerId, RawMaterial assignedMaterial) {
        this.warehouseId = warehouseId;
        this.warehouseNumber = warehouseNumber;
        this.sellerId = sellerId;
        this.assignedMaterial = assignedMaterial;
        this.maxCapacity = 500_000.0; // 500 kt
        this.currentCapacity = 0.0;

        addDomainEvent(new WarehouseCreatedEvent(warehouseId, warehouseNumber, sellerId, assignedMaterial.getName(), maxCapacity));
    }

    public Warehouse(UUID warehouseId, String warehouseNumber, UUID sellerId, RawMaterial assignedMaterial, double currentCapacity, long version) {
        this.warehouseId = warehouseId;
        this.warehouseNumber = warehouseNumber;
        this.sellerId = sellerId;
        this.assignedMaterial = assignedMaterial;
        this.maxCapacity = 500_000.0; // 500 kt
        this.currentCapacity = currentCapacity;
        this.version = version;
    }

    public void setCurrentCapacity(double currentCapacity) {
        this.currentCapacity = currentCapacity;
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

    // Event sourcing method 1: PayloadDeliveredEvent
    public void deliverPayload(double amount, String materialType, String licensePlate) {
        if (!assignedMaterial.getName().equals(materialType)) {
            throw new IllegalArgumentException("Cannot store " + materialType + " in warehouse assigned to " + assignedMaterial.getName());
        }
        
        if (currentCapacity + amount > maxCapacity * 1.1) {
            throw new IllegalStateException("Warehouse would exceed overflow capacity");
        }
        
        currentCapacity += amount;
        version++;
        
        addDomainEvent(new PayloadDeliveredEvent(warehouseId, amount, materialType, licensePlate, currentCapacity));
    }

    // Event sourcing method 2: VesselLoadedEvent
    public void loadVessel(double amount, String materialType) {
        if (!assignedMaterial.getName().equals(materialType)) {
            throw new IllegalArgumentException("Cannot load " + materialType + " from warehouse assigned to " + assignedMaterial.getName());
        }
        
        if (currentCapacity < amount) {
            throw new IllegalStateException("Insufficient material in warehouse");
        }
        
        currentCapacity -= amount;
        version++;
        
        addDomainEvent(new VesselLoadedEvent(warehouseId, amount, materialType, currentCapacity));
    }

    private void addDomainEvent(Object event) {
        domainEvents.add(event);
    }

    public List<Object> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
} 