package be.kdg.prog6.warehousingContext.domain;

import lombok.Getter;
import java.util.UUID;

@Getter
public class WarehouseProjection {
    private final UUID warehouseId;
    private final String warehouseNumber;
    private final UUID sellerId;
    private final String assignedMaterial;
    private final double maxCapacity;
    private double currentCapacity;
    
    public WarehouseProjection(UUID warehouseId, String warehouseNumber, UUID sellerId,
                              String assignedMaterial, double maxCapacity, double currentCapacity) {
        this.warehouseId = warehouseId;
        this.warehouseNumber = warehouseNumber;
        this.sellerId = sellerId;
        this.assignedMaterial = assignedMaterial;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }
    
    public void setCurrentCapacity(double currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
    
    // Applying the activity state to the projection
    public void applyActivity(WarehouseActivity activity) {
        switch (activity.getAction()) {
            case PAYLOAD_DELIVERED -> 
                this.currentCapacity += activity.getAmount();
            case LOADING_VESSEL -> 
                this.currentCapacity -= activity.getAmount();
        }
        
        // Ensuring capacity doesn't go negative
        if (this.currentCapacity < 0) {
            this.currentCapacity = 0;
        }
    }
} 