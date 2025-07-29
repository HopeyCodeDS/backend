package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TruckMovementDatabaseAdapter implements TruckMovementRepositoryPort {
    
    private final TruckMovementJpaRepository truckMovementJpaRepository;
    private final TruckMovementMapper truckMovementMapper;
    
    @Override
    public void save(TruckMovement truckMovement) {
        TruckMovementJpaEntity jpaEntity = truckMovementMapper.toJpaEntity(truckMovement);
        truckMovementJpaRepository.save(jpaEntity);
    }
    
    @Override
    public Optional<TruckMovement> findById(UUID movementId) {
        return truckMovementJpaRepository.findById(movementId)
                .map(truckMovementMapper::toDomain);
    }
    
    @Override
    public Optional<TruckMovement> findByLicensePlate(String licensePlate) {
        return truckMovementJpaRepository.findByLicensePlate(licensePlate)
                .map(truckMovementMapper::toDomain);
    }
} 