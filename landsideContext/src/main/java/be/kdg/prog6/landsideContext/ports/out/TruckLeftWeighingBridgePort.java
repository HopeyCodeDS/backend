package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.TruckMovement;
 
public interface TruckLeftWeighingBridgePort {
    void truckLeftWeighingBridge(TruckMovement truckMovement, String rawMaterialName, String sellerId);
} 