package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import be.kdg.prog6.warehousingContext.domain.WarehouseProjection;
import be.kdg.prog6.warehousingContext.ports.in.ProjectWarehouseActivityUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseProjectionPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectWarehouseActivityUseCaseImpl implements ProjectWarehouseActivityUseCase {
    
    private final WarehouseProjectionPort warehouseProjectionPort;
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    
    @Override
    public WarehouseProjection projectWarehouseActivity(WarehouseActivity activity) {
        log.info("Projecting warehouse activity: {} for warehouse: {}", 
            activity.getAction().getDisplayName(), activity.getWarehouseId());
        
        // Load the current projection
        Optional<WarehouseProjection> warehouseOpt = warehouseProjectionPort.loadWarehouseProjection(activity.getWarehouseId());
        
        if (warehouseOpt.isEmpty()) {
            throw new IllegalStateException("Warehouse projection not found for warehouse: " + activity.getWarehouseId());
        }
        
        // Get the current projection
        WarehouseProjection warehouseProjection = warehouseOpt.get();
        
        // Apply activity to projection
        warehouseProjection.applyActivity(activity);
        
        // Save the updated projection
        warehouseProjectionPort.saveWarehouseProjection(warehouseProjection);
        
        log.info("Successfully projected warehouse activity. New capacity: {} tons", warehouseProjection.getCurrentCapacity());

        // Update the main warehouse
        updateMainWarehouseEntity(activity);
        
        return warehouseProjection;
    }

    private void updateMainWarehouseEntity(WarehouseActivity activity) {
        Optional<Warehouse> warehouseOpt = warehouseRepositoryPort.findById(activity.getWarehouseId());
        if (warehouseOpt.isPresent()) {
            Warehouse warehouse = warehouseOpt.get();
            
            switch (activity.getAction()) {
                case MATERIAL_DELIVERED -> warehouse.addCapacity(activity.getAmount());
                case LOADING_VESSEL, MATERIAL_SHIPPED -> warehouse.addCapacity(-activity.getAmount());
                default -> throw new IllegalArgumentException("Unexpected value: " + activity.getAction());
            }
            
            warehouseRepositoryPort.save(warehouse);
        }
    }
    
} 