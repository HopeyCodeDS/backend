package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import be.kdg.prog6.landsideContext.ports.out.WeighingBridgeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void save(WeighingBridge weighingBridge) {
        // Weighing bridges are pre-populated, so I will only update existing ones
        Optional<WeighingBridgeJpaEntity> existingEntity = 
            weighingBridgeJpaRepository.findByBridgeNumber(weighingBridge.getBridgeNumber());
        
        if (existingEntity.isPresent()) {
            // Update existing entity - only the availability status
            WeighingBridgeJpaEntity entity = existingEntity.get();
            entity.setAvailable(weighingBridge.isAvailable());
            weighingBridgeJpaRepository.save(entity);
        } else {
            // This should never happen since weighing bridges are pre-populated
            throw new IllegalStateException(
                "Weighing bridge with number '" + weighingBridge.getBridgeNumber() + 
                "' not found. Weighing bridges are pre-populated and should not be created dynamically."
            );
        }
    }
}