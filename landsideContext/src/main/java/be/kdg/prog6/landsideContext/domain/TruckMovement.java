package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TruckMovement {
    private final UUID movementId;
    private final LicensePlate licensePlate;
    private final LocalDateTime entryTime;
    // Package-private methods for mapper use only
    @Setter
    private TruckLocation currentLocation;
    @Setter
    private String assignedBridgeNumber;
    @Setter
    @Getter
    private String exitWeighbridgeNumber;
    private Double truckWeight;
    private String assignedWarehouse;
    @Setter
    private LocalDateTime bridgeAssignmentTime;
    
    public TruckMovement(UUID movementId, LicensePlate licensePlate, LocalDateTime entryTime) {
        this.movementId = movementId;
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.currentLocation = TruckLocation.GATE;
        this.exitWeighbridgeNumber = null;
    }

    public static TruckMovement startAtGate(LicensePlate plate, LocalDateTime entryTime) {
        return new TruckMovement(UUID.randomUUID(), plate, entryTime);
    }
    
    public void assignWeighingBridge(String bridgeNumber, LocalDateTime bridgeAssignmentTime) {
        if (isAtGate()) {
            throw new IllegalStateException("Truck must be at gate to assign weighing bridge");
        }
        
        this.assignedBridgeNumber = bridgeNumber;
        this.bridgeAssignmentTime = bridgeAssignmentTime;
    }
    
    public void enterWeighingBridge() {
        if (isAtGate() || !hasAssignedBridge()) {
            throw new IllegalStateException("Truck must be at gate with assigned bridge to enter weighing bridge");
        }
        
        this.currentLocation = TruckLocation.WEIGHING_BRIDGE;
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
        if (!isReadyForWarehouseAssignment()) {
            throw new IllegalStateException("Truck must be ready for warehouse assignment to assign warehouse");
        }
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
        return currentLocation != TruckLocation.GATE;
    }
    
    public boolean hasAssignedBridge() {
        return assignedBridgeNumber != null;
    }

    public void recordWeighing(double truckWeight) {
        if (!isAtWeighingBridge()) {
            throw new IllegalStateException("Truck must be at weighing bridge to record weighing");
        }
        this.truckWeight = truckWeight;
    }

    public boolean isReadyForWarehouseAssignment() {
        return truckWeight != null;
    }
} 