package be.kdg.prog6.landsideContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TruckJpaRepository extends JpaRepository<TruckJpaEntity, UUID> {
    Optional<TruckJpaEntity> findByLicensePlate(String licensePlate);
} 