package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepositoryPort {
    Optional<Warehouse> findByWarehouseId(String warehouseId);
    void save(Warehouse warehouse);
    Optional<Warehouse> findByCustomerIdAndMaterialType(String customerId, String materialType);
    List<Warehouse> findAll();
}
