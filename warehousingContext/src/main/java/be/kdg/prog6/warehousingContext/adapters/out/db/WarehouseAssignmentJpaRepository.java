package be.kdg.prog6.warehousingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface WarehouseAssignmentJpaRepository extends JpaRepository<WarehouseAssignmentJpaEntity, UUID> {
    List<WarehouseAssignmentJpaEntity> findByWarehouseId(UUID warehouseId);
    List<WarehouseAssignmentJpaEntity> findByLicensePlate(String licensePlate);
} 