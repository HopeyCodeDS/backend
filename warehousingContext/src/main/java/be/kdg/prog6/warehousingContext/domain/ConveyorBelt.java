package be.kdg.prog6.warehousingContext.domain;

import lombok.Getter;
import java.util.UUID;

@Getter
public class ConveyorBelt {
    private final UUID conveyorBeltId;
    private final String conveyorBeltNumber;
    private final String warehouseNumber;
    private final RawMaterial assignedMaterial;
    private final boolean isActive;
    private boolean isRunning;

    public ConveyorBelt(UUID conveyorBeltId, String conveyorBeltNumber, 
                       String warehouseNumber, RawMaterial assignedMaterial) {
        this.conveyorBeltId = conveyorBeltId;
        this.conveyorBeltNumber = conveyorBeltNumber;
        this.warehouseNumber = warehouseNumber;
        this.assignedMaterial = assignedMaterial;
        this.isActive = true;
        this.isRunning = false;
    }

    public boolean canHandleMaterial(RawMaterial material) {
        return assignedMaterial.getName().equals(material.getName());
    }

    public void startConveyorBelt() {
        if (isActive && !isRunning) {
            this.isRunning = true;
        }
    }

    public void stopConveyorBelt() {
        this.isRunning = false;
    }
} 