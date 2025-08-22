package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import java.util.Optional;
import java.util.UUID;

public interface GetWarehouseByIdUseCase {
    Optional<Warehouse> getWarehouseById(UUID warehouseId);
}
