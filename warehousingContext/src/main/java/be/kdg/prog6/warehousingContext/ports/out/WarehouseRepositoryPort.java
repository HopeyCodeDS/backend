package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseRepositoryPort {
    List<Warehouse> findAvailableWarehouses(UUID sellerId, String rawMaterialName);
    Optional<Warehouse> findById(UUID warehouseId);
    Optional<Warehouse> findByWarehouseNumber(String warehouseNumber);
    void save(Warehouse warehouse);
    List<Warehouse> findAll();
    double getTotalRawMaterialInWarehouses();
    void deleteById(UUID warehouseId);
} 