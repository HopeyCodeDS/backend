package be.kdg.prog6.warehousingContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class WarehouseAssignment {
    private final UUID assignmentId;
    private final UUID warehouseId;
    private final String licensePlate;
    private final String warehouseNumber;
    private final String rawMaterialName;
    private final String sellerId;
    private final double truckWeight;
    private final LocalDateTime assignedAt;

    public WarehouseAssignment(UUID assignmentId, UUID warehouseId, String licensePlate, 
                             String warehouseNumber, String rawMaterialName, String sellerId, 
                             double truckWeight, LocalDateTime assignedAt) {
        this.assignmentId = assignmentId;
        this.warehouseId = warehouseId;
        this.licensePlate = licensePlate;
        this.warehouseNumber = warehouseNumber;
        this.rawMaterialName = rawMaterialName;
        this.sellerId = sellerId;
        this.truckWeight = truckWeight;
        this.assignedAt = assignedAt;
    }

    // Getters
    public UUID getAssignmentId() { return assignmentId; }
    public UUID getWarehouseId() { return warehouseId; }
    public String getLicensePlate() { return licensePlate; }
    public String getWarehouseNumber() { return warehouseNumber; }
    public String getRawMaterialName() { return rawMaterialName; }
    public String getSellerId() { return sellerId; }
    public double getTruckWeight() { return truckWeight; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
} 