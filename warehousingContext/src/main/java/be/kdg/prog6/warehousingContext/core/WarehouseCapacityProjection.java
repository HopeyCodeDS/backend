package be.kdg.prog6.warehousingContext.core;


import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.ports.in.WarehouseProjectionUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WarehouseCapacityProjection implements WarehouseProjectionUseCase {

    private final WarehouseRepositoryPort warehouseRepositoryPort;


    public WarehouseCapacityProjection(WarehouseRepositoryPort warehouseRepositoryPort) {
        this.warehouseRepositoryPort = warehouseRepositoryPort;
    }


    public List<Warehouse> getAllWarehousesWithCapacityInfo() {
        return warehouseRepositoryPort.findAll();
    }

    @Override
    public void projectWarehouse(String warehouseId) {

    }
}
