package be.kdg.prog6.warehousingContext.ports.in;

import java.util.UUID;

public interface DeleteWarehouseUseCase {
    void deleteWarehouse(UUID warehouseId);
}
