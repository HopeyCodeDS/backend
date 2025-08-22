package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.WeighingBridge;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WeighingBridgeRepositoryPort {
    List<WeighingBridge> findAvailableBridges();
    Optional<WeighingBridge> findById(UUID bridgeId);
    Optional<WeighingBridge> findByBridgeNumber(String bridgeNumber);
    void save(WeighingBridge weighingBridge);
}
