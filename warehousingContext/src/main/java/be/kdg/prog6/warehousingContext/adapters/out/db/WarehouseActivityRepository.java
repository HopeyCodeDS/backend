package be.kdg.prog6.warehousingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WarehouseActivityRepository extends JpaRepository<WarehouseActivityJpaEntity, UUID> {
    
    List<WarehouseActivityJpaEntity> findByWarehouseIdOrderByPointInTimeDesc(UUID warehouseId);
    
    @Query("SELECT wa FROM WarehouseActivityJpaEntity wa WHERE wa.warehouseId = :warehouseId AND wa.pointInTime BETWEEN :from AND :to ORDER BY wa.pointInTime DESC")
    List<WarehouseActivityJpaEntity> findByWarehouseIdAndTimeRange(@Param("warehouseId") UUID warehouseId, 
                                                                  @Param("from") LocalDateTime from, 
                                                                  @Param("to") LocalDateTime to);
    
    @Query("SELECT wa FROM WarehouseActivityJpaEntity wa WHERE wa.warehouseId = :warehouseId ORDER BY wa.pointInTime DESC")
    List<WarehouseActivityJpaEntity> findRecentActivities(@Param("warehouseId") UUID warehouseId, 
                                                         org.springframework.data.domain.Pageable pageable);
} 