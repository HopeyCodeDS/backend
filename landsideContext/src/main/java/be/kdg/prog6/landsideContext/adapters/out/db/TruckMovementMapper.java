package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import org.springframework.stereotype.Component;

@Component
public class TruckMovementMapper {
    
    public TruckMovementJpaEntity toJpaEntity(TruckMovement truckMovement) {
        TruckMovementJpaEntity jpaEntity = new TruckMovementJpaEntity();
        jpaEntity.setMovementId(truckMovement.getMovementId());
        jpaEntity.setLicensePlate(truckMovement.getLicensePlate().getValue());
        jpaEntity.setEntryTime(truckMovement.getEntryTime());
        jpaEntity.setCurrentLocation(truckMovement.getCurrentLocation());
        
        if (truckMovement.getAssignedBridgeNumber() != null && !truckMovement.getAssignedBridgeNumber().equals("Not assigned")) {
            jpaEntity.setAssignedBridgeNumber(truckMovement.getAssignedBridgeNumber());
            jpaEntity.setBridgeAssignmentTime(truckMovement.getBridgeAssignmentTime());
        }
        
        if (truckMovement.getExitWeighbridgeNumber() != null && !truckMovement.getExitWeighbridgeNumber().equals("Not assigned")) {
            jpaEntity.setExitWeighbridgeNumber(truckMovement.getExitWeighbridgeNumber());
        }
        
        jpaEntity.setTruckWeight(truckMovement.getTruckWeight());
        jpaEntity.setAssignedWarehouse(truckMovement.getAssignedWarehouse());
        
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
        
        // Set assigned bridge number if exists
        if (jpaEntity.getAssignedBridgeNumber() != null) {
            truckMovement.setAssignedBridgeNumber(jpaEntity.getAssignedBridgeNumber());
            truckMovement.setBridgeAssignmentTime(jpaEntity.getBridgeAssignmentTime());
        }

        if (jpaEntity.getExitWeighbridgeNumber() != null) {
            truckMovement.setExitWeighbridgeNumber(jpaEntity.getExitWeighbridgeNumber());
        }
        
        // Add missing mappings
        if (jpaEntity.getTruckWeight() != null || jpaEntity.getAssignedWarehouse() != null) {
            truckMovement.restoreState(jpaEntity.getTruckWeight(), jpaEntity.getAssignedWarehouse());
        }
        if (jpaEntity.getAssignedWarehouse() != null) {
            truckMovement.assignWarehouse(jpaEntity.getAssignedWarehouse());
        }
        
        return truckMovement;
    }
} 