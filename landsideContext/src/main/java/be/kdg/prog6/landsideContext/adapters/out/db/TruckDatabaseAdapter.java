package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
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

    @Override
    @Transactional(readOnly = true)
    public List<Truck> findAll() {
        return truckJpaRepository.findAll().stream()
                .map(truckMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(UUID truckId) {
        truckJpaRepository.deleteById(truckId);
    }
} 