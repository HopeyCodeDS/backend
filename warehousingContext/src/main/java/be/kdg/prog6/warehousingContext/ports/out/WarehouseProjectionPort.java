package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.WarehouseProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseProjectionPort {
    Optional<WarehouseProjection> loadWarehouseProjection(UUID warehouseId);
    void saveWarehouseProjection(WarehouseProjection warehouseProjection);
    //List<WarehouseProjection> findByMaterialType(String materialType);
} 