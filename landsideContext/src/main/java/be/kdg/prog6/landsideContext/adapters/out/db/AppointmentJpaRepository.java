package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentJpaRepository extends JpaRepository<AppointmentJpaEntity, UUID> {
    
    @Query("SELECT a FROM AppointmentJpaEntity a WHERE a.arrivalWindowStart <= :endTime AND a.arrivalWindowEnd >= :startTime")
    List<AppointmentJpaEntity> findByArrivalWindowOverlap(@Param("startTime") LocalDateTime startTime, 
                                                         @Param("endTime") LocalDateTime endTime);
    
    List<AppointmentJpaEntity> findBySellerId(UUID sellerId);

    
    @Query("SELECT a FROM AppointmentJpaEntity a WHERE a.truck.licensePlate = :licensePlate")
    List<AppointmentJpaEntity> findByLicensePlate(@Param("licensePlate") String licensePlate);

    @Query("SELECT a FROM AppointmentJpaEntity a WHERE a.status = :status")
    List<AppointmentJpaEntity> findByStatus(@Param("status") AppointmentStatus status);
}