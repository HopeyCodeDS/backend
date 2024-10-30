package be.kdg.prog6.landsideContext.adapters.out.db.repositories;


import be.kdg.prog6.landsideContext.adapters.out.db.entities.AppointmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentJpaRepository extends JpaRepository<AppointmentJpaEntity, Long> {
    Optional<AppointmentJpaEntity> findBySellerId(String sellerId);
    List<AppointmentJpaEntity> findByArrivalWindowBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT a FROM AppointmentJpaEntity a JOIN FETCH a.slot WHERE a.truck.licensePlate = :licensePlate")
    Optional<AppointmentJpaEntity> findAppointmentsWithSlotByTruckLicensePlate(@Param("licensePlate")String licensePlate);

}
