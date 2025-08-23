package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.WarehouseStatus;

public interface GetWarehouseStatusUseCase {
    WarehouseStatus getWarehouseStatus(String licensePlate);
}
