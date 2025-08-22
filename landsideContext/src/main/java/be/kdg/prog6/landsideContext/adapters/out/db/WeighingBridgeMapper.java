package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import org.springframework.stereotype.Component;

@Component
public class WeighingBridgeMapper {
    
    public WeighingBridgeJpaEntity toJpaEntity(WeighingBridge weighingBridge) {
        WeighingBridgeJpaEntity jpaEntity = new WeighingBridgeJpaEntity();
        jpaEntity.setBridgeId(weighingBridge.getBridgeId());
        jpaEntity.setBridgeNumber(weighingBridge.getBridgeNumber());
        jpaEntity.setAvailable(weighingBridge.isAvailable());
        return jpaEntity;
    }
    
    public WeighingBridge toDomain(WeighingBridgeJpaEntity jpaEntity) {
        return new WeighingBridge(jpaEntity.getBridgeId(), jpaEntity.getBridgeNumber(), jpaEntity.isAvailable());
    }
}