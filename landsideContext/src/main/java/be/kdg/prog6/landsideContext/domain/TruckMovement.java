package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
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

    public static TruckMovement startAtGate(LicensePlate plate, LocalDateTime entryTime) {
        return new TruckMovement(UUID.randomUUID(), plate, entryTime);
    }
    
    public void assignWeighingBridge(String bridgeNumber, LocalDateTime bridgeAssignmentTime) {
        if (!isAtGate()) {
            throw new IllegalStateException("Truck must be at gate to assign weighing bridge");
        }
        this.assignedBridgeNumber = bridgeNumber;
        this.bridgeAssignmentTime = bridgeAssignmentTime;
    }
    
    public void enterWeighingBridge() {
        if (!isAtGate() || !hasAssignedBridge()) {
            throw new IllegalStateException("Truck must be at gate with assigned bridge to enter weighing bridge");
        }
        
        this.currentLocation = TruckLocation.WEIGHING_BRIDGE;
        System.out.println("=== TRUCK LOCATION UPDATE ===");
        System.out.printf("Truck %s moved from GATE to WEIGHING_BRIDGE\n", licensePlate.getValue());
    }

    public boolean hasExitWeighbridgeNumber() {
        return exitWeighbridgeNumber != null && !exitWeighbridgeNumber.trim().isEmpty();
    }
    
    public void leaveWeighingBridge() {
        if (currentLocation != TruckLocation.WEIGHING_BRIDGE) {
            throw new IllegalStateException("Truck must be at weighing bridge to leave");
        }
        
        this.currentLocation = TruckLocation.WAREHOUSE;
        System.out.println("=== TRUCK LOCATION UPDATE ===");
        System.out.printf("Truck %s moved from WEIGHING_BRIDGE to WAREHOUSE\n", licensePlate.getValue());
    }

    // Method to assign warehouse
    public void assignWarehouse(String warehouseNumber) {
        if (!isReadyForWarehouseAssignment()) {
            throw new IllegalStateException("Truck must be ready for warehouse assignment to assign warehouse");
        }
        this.assignedWarehouse = warehouseNumber;
        System.out.println("=== TRUCK LOCATION UPDATE ===");
        System.out.printf("Truck %s assigned to warehouse %s\n", licensePlate.getValue(), warehouseNumber);
    }
    
    public void exitFacility() {
        if (currentLocation != TruckLocation.WAREHOUSE) {
            throw new IllegalStateException("Truck must be at warehouse to exit facility");
        }
        
        this.currentLocation = TruckLocation.EXIT;
        System.out.println("=== TRUCK LOCATION UPDATE ===");
        System.out.printf("Truck %s moved from WAREHOUSE to EXIT\n", licensePlate.getValue());
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

    public void recordWeighing(double truckWeight) {
        if (!isAtWeighingBridge()) {
            throw new IllegalStateException("Truck must be at weighing bridge to record weighing");
        }
        this.truckWeight = truckWeight;
    }

    public boolean isReadyForWarehouseAssignment() {
        return truckWeight != null;
    }

    public void restoreState(Double truckWeight, String assignedWarehouse) {
        this.truckWeight = truckWeight;
        this.assignedWarehouse = assignedWarehouse;
    }
} 