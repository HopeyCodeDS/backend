package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.WarehouseCreateRequestDto;
import be.kdg.prog6.warehousingContext.domain.RawMaterial;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.ports.in.CreateWarehouseUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateWarehouseUseCaseImpl implements CreateWarehouseUseCase {
    
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    
    @Override
    @Transactional
    public Warehouse createWarehouse(WarehouseCreateRequestDto request) {
        // Validate request
        if (request.getWarehouseNumber() == null || request.getWarehouseNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse number is required");
        }
        if (request.getSellerId() == null) {
            throw new IllegalArgumentException("Seller ID is required");
        }
        if (request.getRawMaterialName() == null || request.getRawMaterialName().trim().isEmpty()) {
            throw new IllegalArgumentException("Raw material name is required");
        }
        if (request.getMaxCapacity() <= 0) {
            throw new IllegalArgumentException("Max capacity must be positive");
        }
        
        // Create raw material
        RawMaterial rawMaterial = RawMaterial.fromName(request.getRawMaterialName());
        
        // Create warehouse
        Warehouse warehouse = new Warehouse(
            UUID.randomUUID(),
            request.getWarehouseNumber(),
            request.getSellerId(),
            rawMaterial
        );
        
        // Save warehouse
        warehouseRepositoryPort.save(warehouse);
        
        return warehouse;
    }
}
