package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import org.springframework.stereotype.Component;

@Component
public class TruckMovementMapper {
    
    public TruckMovementJpaEntity toJpaEntity(TruckMovement truckMovement) {
        TruckMovementJpaEntity jpaEntity = new TruckMovementJpaEntity();
        jpaEntity.setMovementId(truckMovement.getMovementId());
        jpaEntity.setLicensePlate(truckMovement.getLicensePlate().getValue());
        jpaEntity.setEntryTime(truckMovement.getEntryTime());
        jpaEntity.setCurrentLocation(truckMovement.getCurrentLocation());
        
        if (truckMovement.getAssignedBridge() != null) {
            jpaEntity.setAssignedBridgeId(truckMovement.getAssignedBridge().getBridgeId());
            jpaEntity.setBridgeAssignmentTime(truckMovement.getBridgeAssignmentTime());
        }
        
        return jpaEntity;
    }
    
    public TruckMovement toDomain(TruckMovementJpaEntity jpaEntity) {
        LicensePlate licensePlate = new LicensePlate(jpaEntity.getLicensePlate());
        
        TruckMovement truckMovement = new TruckMovement(
            jpaEntity.getMovementId(),
            licensePlate,
            jpaEntity.getEntryTime()
        );
        
        // Set current location
        truckMovement.setCurrentLocation(jpaEntity.getCurrentLocation());
        
        // Set assigned bridge if exists
        if (jpaEntity.getAssignedBridgeId() != null && jpaEntity.getAssignedBridge() != null) {
            WeighingBridge weighingBridge = new WeighingBridge(
                jpaEntity.getAssignedBridge().getBridgeId(),
                jpaEntity.getAssignedBridge().getBridgeNumber()
            );
            truckMovement.setAssignedBridge(weighingBridge);
            truckMovement.setBridgeAssignmentTime(jpaEntity.getBridgeAssignmentTime());
        }
        
        return truckMovement;
    }
} 