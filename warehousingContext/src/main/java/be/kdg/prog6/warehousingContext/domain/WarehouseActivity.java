package be.kdg.prog6.warehousingContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class WarehouseActivity {
    private final UUID activityId;
    private final UUID warehouseId;
    private final double amount;
    private final WarehouseActivityAction action;
    private final LocalDateTime pointInTime;
    private final String materialType;
    private final String licensePlate;
    private final String description;
    
    public WarehouseActivity(UUID warehouseId, double amount, WarehouseActivityAction action, 
                           String materialType, String licensePlate, String description) {
        this.activityId = UUID.randomUUID();
        this.warehouseId = warehouseId;
        this.amount = amount;
        this.action = action;
        this.pointInTime = LocalDateTime.now();
        this.materialType = materialType;
        this.licensePlate = licensePlate;
        this.description = description;
    }
}