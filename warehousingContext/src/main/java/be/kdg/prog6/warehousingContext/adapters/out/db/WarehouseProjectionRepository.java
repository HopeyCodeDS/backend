package be.kdg.prog6.warehousingContext.adapters.out.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseProjectionRepository extends JpaRepository<WarehouseProjectionJpaEntity, UUID> {

}
