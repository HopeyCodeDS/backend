package be.kdg.prog6.landsideContext.adapters.out.db.repositories;


import be.kdg.prog6.landsideContext.adapters.out.db.entities.AppointmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentJpaRepository extends JpaRepository<AppointmentJpaEntity, Long> {
    Optional<AppointmentJpaEntity> findBySellerId(UUID sellerId);
    List<AppointmentJpaEntity> findByArrivalWindowBetween(LocalDateTime start, LocalDateTime end);
    Optional<AppointmentJpaEntity> findByTruck_LicensePlate(String licensePlate);

}
