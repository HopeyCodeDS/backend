package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TruckDatabaseAdapter implements TruckRepositoryPort {
    
    private final TruckJpaRepository truckJpaRepository;
    private final TruckMapper truckMapper;
    
    @Override
    @Transactional
    public void save(Truck truck) {
        TruckJpaEntity jpaEntity = truckMapper.toJpaEntity(truck);
        truckJpaRepository.save(jpaEntity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Truck> findById(UUID truckId) {
        return truckJpaRepository.findById(truckId)
                .map(truckMapper::toDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Truck> findByLicensePlate(String licensePlate) {
        return truckJpaRepository.findByLicensePlate(licensePlate)
                .map(truckMapper::toDomain);
    }
} 