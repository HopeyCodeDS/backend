package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.WarehouseAssignment;
import java.util.List;
import java.util.UUID;

public interface WarehouseAssignmentRepositoryPort {
    void save(WarehouseAssignment assignment);
    List<WarehouseAssignment> findByWarehouseId(UUID warehouseId);
    List<WarehouseAssignment> findByLicensePlate(String licensePlate);
}