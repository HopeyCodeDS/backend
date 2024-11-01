package be.kdg.prog6.landsideContext.adapters.out.db.repositories;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.TruckDispatchRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TruckDispatchRecordJpaRepository extends JpaRepository<TruckDispatchRecordEntity, Long> {
    Optional<TruckDispatchRecordEntity> findByLicensePlate(String licensePlate);
}
