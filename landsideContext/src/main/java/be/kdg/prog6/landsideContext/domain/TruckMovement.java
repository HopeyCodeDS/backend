package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TruckMovement {
    private final UUID movementId;
    private final LicensePlate licensePlate;
    private final LocalDateTime entryTime;
    private TruckLocation currentLocation;
    private String assignedBridgeNumber;
    private String exitWeighbridgeNumber;
    private Double truckWeight;
    private String assignedWarehouse;
    private LocalDateTime bridgeAssignmentTime;
    
    public TruckMovement(UUID movementId, LicensePlate licensePlate, LocalDateTime entryTime) {
        this.movementId = movementId;
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.currentLocation = TruckLocation.GATE;
        this.exitWeighbridgeNumber = null;
    }
    
    public void assignWeighingBridge(String bridgeNumber) {
        if (currentLocation != TruckLocation.GATE) {
            throw new IllegalStateException("Truck must be at gate to assign weighing bridge");
        }
        
        this.assignedBridgeNumber = bridgeNumber;
        this.currentLocation = TruckLocation.WEIGHING_BRIDGE;
        this.bridgeAssignmentTime = LocalDateTime.now();
    }
    
    public void enterWeighingBridge() {
        if (currentLocation != TruckLocation.GATE || assignedBridgeNumber == null) {
            throw new IllegalStateException("Truck must be at gate with assigned bridge to enter weighing bridge");
        }
        
        this.currentLocation = TruckLocation.WEIGHING_BRIDGE;
    }

    public void setExitWeighbridgeNumber(String exitWeighbridgeNumber) {
        this.exitWeighbridgeNumber = exitWeighbridgeNumber;
    }

    public String getExitWeighbridgeNumber() {
        return exitWeighbridgeNumber;
    }

    public boolean hasExitWeighbridgeNumber() {
        return exitWeighbridgeNumber != null && !exitWeighbridgeNumber.trim().isEmpty();
    }
    
    public void leaveWeighingBridge() {
        if (currentLocation != TruckLocation.WEIGHING_BRIDGE) {
            throw new IllegalStateException("Truck must be at weighing bridge to leave");
        }
        
        this.currentLocation = TruckLocation.WAREHOUSE;
    }

    // Method to assign warehouse
    public void assignWarehouse(String warehouseNumber) {
        this.assignedWarehouse = warehouseNumber;
        this.currentLocation = TruckLocation.WAREHOUSE;
    }
    
    public void exitFacility() {
        if (currentLocation != TruckLocation.WAREHOUSE) {
            throw new IllegalStateException("Truck must be at warehouse to exit facility");
        }
        
        this.currentLocation = TruckLocation.EXIT;
    }
    
    public boolean isAtWeighingBridge() {
        return currentLocation == TruckLocation.WEIGHING_BRIDGE;
    }
    
    public boolean isOnSite() {
        return currentLocation != TruckLocation.EXIT;
    }
    
    public String getAssignedBridgeNumber() {
        return assignedBridgeNumber != null ? assignedBridgeNumber : "Not assigned";
    }
    
    public boolean isAtGate() {
        return currentLocation == TruckLocation.GATE;
    }
    
    public boolean hasAssignedBridge() {
        return assignedBridgeNumber != null;
    }
    
    // Package-private methods for mapper use only
    public void setCurrentLocation(TruckLocation location) {
        this.currentLocation = location;
    }
    
    public void setAssignedBridgeNumber(String bridgeNumber) {
        this.assignedBridgeNumber = bridgeNumber;
    }
    
    public void setBridgeAssignmentTime(LocalDateTime time) {
        this.bridgeAssignmentTime = time;
    }

    public void recordWeighing(double truckWeight) {
        this.truckWeight = truckWeight;
        this.currentLocation = TruckLocation.WEIGHING_BRIDGE;
    }

    public boolean isReadyForWarehouseAssignment() {
        return truckWeight != null;
    }
} 