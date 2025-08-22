package be.kdg.prog6.landsideContext.domain;

import lombok.Value;
import java.util.UUID;

@Value
public class WeighingBridge {
    UUID bridgeId;
    String bridgeNumber;
    boolean isAvailable;
    
    public WeighingBridge(UUID bridgeId, String bridgeNumber) {
        this.bridgeId = bridgeId;
        this.bridgeNumber = bridgeNumber;
        this.isAvailable = true;
    }
    
    public WeighingBridge markAsOccupied() {
        return new WeighingBridge(bridgeId, bridgeNumber, false);
    }
    
    public WeighingBridge markAsAvailable() {
        return new WeighingBridge(bridgeId, bridgeNumber, true);
    }
    
    public WeighingBridge(UUID bridgeId, String bridgeNumber, boolean isAvailable) {
        this.bridgeId = bridgeId;
        this.bridgeNumber = bridgeNumber;
        this.isAvailable = isAvailable;
    }
} 