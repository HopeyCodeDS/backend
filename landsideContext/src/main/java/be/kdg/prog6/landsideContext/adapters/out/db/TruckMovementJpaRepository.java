package be.kdg.prog6.landsideContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import be.kdg.prog6.landsideContext.domain.TruckLocation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TruckMovementJpaRepository extends JpaRepository<TruckMovementJpaEntity, UUID> {
    
    @Query("SELECT tm FROM TruckMovementJpaEntity tm WHERE tm.licensePlate = :licensePlate")
    Optional<TruckMovementJpaEntity> findByLicensePlate(@Param("licensePlate") String licensePlate);

    @Query("SELECT tm FROM TruckMovementJpaEntity tm WHERE tm.currentLocation NOT IN (:excludedLocations)")
    List<TruckMovementJpaEntity> findByCurrentLocationNotIn(@Param("excludedLocations") List<TruckLocation> excludedLocations);

    @Query("SELECT COUNT(tm) FROM TruckMovementJpaEntity tm WHERE tm.currentLocation NOT IN (:excludedLocations)")
    long countByCurrentLocationNotIn(@Param("excludedLocations") List<TruckLocation> excludedLocations);

}