package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import java.util.List;

public interface GetWarehouseOverviewUseCase {
    List<Warehouse> getAllWarehouses();
    double getTotalRawMaterialInWarehouses();
} 