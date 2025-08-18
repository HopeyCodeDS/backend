package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.TruckMovement;

import java.util.UUID;

public interface TruckLeftWeighingBridgePort {
    void truckLeftWeighingBridge(TruckMovement truckMovement, String rawMaterialName, UUID sellerId);
} 