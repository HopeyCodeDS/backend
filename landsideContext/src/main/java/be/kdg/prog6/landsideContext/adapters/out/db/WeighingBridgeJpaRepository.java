package be.kdg.prog6.landsideContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeighingBridgeJpaRepository extends JpaRepository<WeighingBridgeJpaEntity, UUID> {
    
    List<WeighingBridgeJpaEntity> findByIsAvailableTrue();
    
    Optional<WeighingBridgeJpaEntity> findByBridgeNumber(String bridgeNumber);
}