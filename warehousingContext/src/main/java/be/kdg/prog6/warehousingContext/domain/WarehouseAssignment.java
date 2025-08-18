package be.kdg.prog6.warehousingContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public class WarehouseAssignment {
    private final UUID assignmentId;
    private final UUID warehouseId;
    private final String licensePlate;
    private final String warehouseNumber;
    private final String rawMaterialName;
    private final UUID sellerId;
    private final double truckWeight;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime assignedAt;

    public WarehouseAssignment(UUID assignmentId, UUID warehouseId, String licensePlate, 
                             String warehouseNumber, String rawMaterialName, UUID sellerId, 
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
} 