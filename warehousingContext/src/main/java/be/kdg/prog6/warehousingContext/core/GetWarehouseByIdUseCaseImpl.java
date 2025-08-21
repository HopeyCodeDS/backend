package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.ports.in.GetWarehouseByIdUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetWarehouseByIdUseCaseImpl implements GetWarehouseByIdUseCase {
    
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    
    @Override
    public Optional<Warehouse> getWarehouseById(UUID warehouseId) {
        return warehouseRepositoryPort.findById(warehouseId);
    }
}
