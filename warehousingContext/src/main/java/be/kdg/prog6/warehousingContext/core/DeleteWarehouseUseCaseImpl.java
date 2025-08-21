package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.ports.in.DeleteWarehouseUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteWarehouseUseCaseImpl implements DeleteWarehouseUseCase {
    
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    
    @Override
    @Transactional
    public void deleteWarehouse(UUID warehouseId) {
        // Check if warehouse exists
        if (!warehouseRepositoryPort.findById(warehouseId).isPresent()) {
            throw new IllegalArgumentException("Warehouse not found");
        }
        
        // Delete warehouse
        warehouseRepositoryPort.deleteById(warehouseId);
    }
}
