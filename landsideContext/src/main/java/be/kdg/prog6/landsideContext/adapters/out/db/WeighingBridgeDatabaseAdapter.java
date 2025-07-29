package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import be.kdg.prog6.landsideContext.ports.out.WeighingBridgeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WeighingBridgeDatabaseAdapter implements WeighingBridgeRepositoryPort {
    
    private final WeighingBridgeJpaRepository weighingBridgeJpaRepository;
    private final WeighingBridgeMapper weighingBridgeMapper;
    
    @Override
    public List<WeighingBridge> findAvailableBridges() {
        List<WeighingBridgeJpaEntity> jpaEntities = weighingBridgeJpaRepository.findByIsAvailableTrue();
        return jpaEntities.stream()
                .map(weighingBridgeMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<WeighingBridge> findById(UUID bridgeId) {
        return weighingBridgeJpaRepository.findById(bridgeId)
                .map(weighingBridgeMapper::toDomain);
    }
    
    @Override
    public Optional<WeighingBridge> findByBridgeNumber(String bridgeNumber) {
        return weighingBridgeJpaRepository.findByBridgeNumber(bridgeNumber)
                .map(weighingBridgeMapper::toDomain);
    }
    
    @Override
    public void save(WeighingBridge weighingBridge) {
        WeighingBridgeJpaEntity jpaEntity = weighingBridgeMapper.toJpaEntity(weighingBridge);
        weighingBridgeJpaRepository.save(jpaEntity);
    }
}