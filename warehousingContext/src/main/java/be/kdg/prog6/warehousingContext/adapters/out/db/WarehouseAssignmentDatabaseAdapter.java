package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.WarehouseAssignment;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseAssignmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WarehouseAssignmentDatabaseAdapter implements WarehouseAssignmentRepositoryPort {
    
    private final WarehouseAssignmentJpaRepository jpaRepository;
    private final WarehouseAssignmentMapper mapper;
    
    @Override
    public void save(WarehouseAssignment assignment) {
        WarehouseAssignmentJpaEntity jpaEntity = mapper.toJpaEntity(assignment);
        jpaRepository.save(jpaEntity);
    }
    
    @Override
    public List<WarehouseAssignment> findByWarehouseId(UUID warehouseId) {
        return jpaRepository.findByWarehouseId(warehouseId)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<WarehouseAssignment> findByLicensePlate(String licensePlate) {
        return jpaRepository.findByLicensePlate(licensePlate)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
} 