package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import be.kdg.prog6.warehousingContext.domain.WarehouseProjection;

public interface ProjectWarehouseActivityUseCase {
    WarehouseProjection projectWarehouseActivity(WarehouseActivity activity);
} 