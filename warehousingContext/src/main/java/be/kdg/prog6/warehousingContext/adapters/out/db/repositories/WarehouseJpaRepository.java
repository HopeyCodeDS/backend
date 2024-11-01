package be.kdg.prog6.warehousingContext.adapters.out.db.repositories;

import be.kdg.prog6.warehousingContext.adapters.out.db.entities.WarehouseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, Long> {
    Optional<WarehouseJpaEntity> findByWarehouseId(String warehouseId);
    Optional<WarehouseJpaEntity> findByCustomerIdAndMaterialType(String customerId, String materialType);
}
