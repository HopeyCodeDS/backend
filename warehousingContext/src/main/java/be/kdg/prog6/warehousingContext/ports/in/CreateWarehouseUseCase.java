package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.adapters.in.web.dto.WarehouseCreateRequestDto;

public interface CreateWarehouseUseCase {
    Warehouse createWarehouse(WarehouseCreateRequestDto request);
}
