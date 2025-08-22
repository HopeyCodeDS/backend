package be.kdg.prog6.warehousingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, UUID> {
    
    @Query("SELECT w FROM WarehouseJpaEntity w WHERE w.sellerId = :sellerId AND w.rawMaterialName = :rawMaterialName")
    List<WarehouseJpaEntity> findBySellerIdAndRawMaterialName(@Param("sellerId") UUID sellerId, 
                                                              @Param("rawMaterialName") String rawMaterialName);
    
    Optional<WarehouseJpaEntity> findByWarehouseNumber(String warehouseNumber);
}
