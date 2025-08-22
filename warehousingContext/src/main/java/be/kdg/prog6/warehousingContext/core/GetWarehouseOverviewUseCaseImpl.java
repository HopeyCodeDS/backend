package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.ports.in.GetWarehouseOverviewUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetWarehouseOverviewUseCaseImpl implements GetWarehouseOverviewUseCase {
    
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    
    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepositoryPort.findAll();
    }
    
    @Override
    public double getTotalRawMaterialInWarehouses() {
        return warehouseRepositoryPort.getTotalRawMaterialInWarehouses();
    }
} 