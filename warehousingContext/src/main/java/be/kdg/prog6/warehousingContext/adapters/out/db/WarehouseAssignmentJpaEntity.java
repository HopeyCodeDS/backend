package be.kdg.prog6.warehousingContext.adapters.out.db;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "warehouse_assignments", schema = "warehousing")
public class WarehouseAssignmentJpaEntity {
    
    @Id
    @Column(name = "assignment_id")
    private UUID assignmentId;
    
    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;
    
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;
    
    @Column(name = "warehouse_number", nullable = false)
    private String warehouseNumber;
    
    @Column(name = "raw_material_name", nullable = false)
    private String rawMaterialName;
    
    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    
    @Column(name = "truck_weight", nullable = false)
    private double truckWeight;
    
    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    // Default constructor
    public WarehouseAssignmentJpaEntity() {}

    // Constructor with all fields
    public WarehouseAssignmentJpaEntity(UUID assignmentId, UUID warehouseId, String licensePlate,
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

    // Getters and Setters
    public UUID getAssignmentId() { return assignmentId; }
    public void setAssignmentId(UUID assignmentId) { this.assignmentId = assignmentId; }
    
    public UUID getWarehouseId() { return warehouseId; }
    public void setWarehouseId(UUID warehouseId) { this.warehouseId = warehouseId; }
    
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    
    public String getWarehouseNumber() { return warehouseNumber; }
    public void setWarehouseNumber(String warehouseNumber) { this.warehouseNumber = warehouseNumber; }
    
    public String getRawMaterialName() { return rawMaterialName; }
    public void setRawMaterialName(String rawMaterialName) { this.rawMaterialName = rawMaterialName; }
    
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    
    public double getTruckWeight() { return truckWeight; }
    public void setTruckWeight(double truckWeight) { this.truckWeight = truckWeight; }
    
    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }
} 