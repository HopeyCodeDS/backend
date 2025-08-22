package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import be.kdg.prog6.warehousingContext.domain.WarehouseProjection;
import be.kdg.prog6.warehousingContext.ports.in.ProjectWarehouseActivityUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseProjectionPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectWarehouseActivityUseCaseImpl implements ProjectWarehouseActivityUseCase {
    
    private final WarehouseProjectionPort warehouseProjectionPort;
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    
    @Override
    @Transactional
    public WarehouseProjection projectWarehouseActivity(WarehouseActivity activity) {
        log.info("Projecting warehouse activity: {} for warehouse: {}", 
            activity.getAction().getDisplayName(), activity.getWarehouseId());
        
        System.out.println("=== WAREHOUSE PROJECTION UPDATE START ===");
        System.out.printf("Activity Type: %s\n", activity.getAction().getDisplayName());
        System.out.printf("Warehouse ID: %s\n", activity.getWarehouseId());
        System.out.printf("Amount: %s tons\n", activity.getAmount());
        System.out.printf("Material: %s\n", activity.getMaterialType());
        System.out.printf("Description: %s\n", activity.getDescription());
        System.out.printf("Timestamp: %s\n", activity.getPointInTime());

        // Load the current projection
        Optional<WarehouseProjection> warehouseOpt = warehouseProjectionPort.loadWarehouseProjection(activity.getWarehouseId());
        
        WarehouseProjection warehouseProjection;

        if (warehouseOpt.isEmpty()) {
            // Create projection on-demand if it doesn't exist
            log.info("Warehouse projection not found for warehouse: {}. Creating new projection.", activity.getWarehouseId());
            warehouseProjection = createWarehouseProjection(activity.getWarehouseId());
        } else {
            log.info("Found existing warehouse projection for warehouse: {} with capacity: {} tons", 
            activity.getWarehouseId(), warehouseOpt.get().getCurrentCapacity());
            warehouseProjection = warehouseOpt.get();
        }
        
        // Apply activity to projection
        warehouseProjection.applyActivity(activity);
        
        // Save the updated projection
        log.info("Saving updated warehouse projection for warehouse: {} with new capacity: {} tons", 
        activity.getWarehouseId(), warehouseProjection.getCurrentCapacity());
        warehouseProjectionPort.saveWarehouseProjection(warehouseProjection);
        
        log.info("Successfully projected warehouse activity. New capacity: {} tons", warehouseProjection.getCurrentCapacity());

        // Update the main warehouse
        updateMainWarehouseEntity(activity);
        
        System.out.println("=== WAREHOUSE PROJECTION UPDATE COMPLETE ===");
        
        return warehouseProjection;
    }
    

    @Transactional
    private WarehouseProjection createWarehouseProjection(UUID warehouseId) {
        // Load the main warehouse entity to get the base data
        Optional<Warehouse> warehouseOpt = warehouseRepositoryPort.findAll().stream()
            .filter(warehouse -> warehouse.getWarehouseId().equals(warehouseId))
            .findFirst();
        
        if (warehouseOpt.isEmpty()) {
            throw new IllegalStateException("Warehouse not found for ID: " + warehouseId);
        }
        
        Warehouse warehouse = warehouseOpt.get();
        
        // Creating a new projection with the warehouse's current state
        WarehouseProjection projection = new WarehouseProjection(
            warehouse.getWarehouseId(),
            warehouse.getWarehouseNumber(),
            warehouse.getSellerId(),
            warehouse.getAssignedMaterial().getName(),
            warehouse.getMaxCapacity(),
            warehouse.getCurrentCapacity()
        );
        
        // Save the initial projection
        warehouseProjectionPort.saveWarehouseProjection(projection);
        
        log.info("Created new warehouse projection for warehouse: {} with initial capacity: {} tons", 
            warehouseId, projection.getCurrentCapacity());
        
        return projection;
    }

    @Transactional
    private void updateMainWarehouseEntity(WarehouseActivity activity) {
        Optional<Warehouse> warehouseOpt = warehouseRepositoryPort.findAll().stream()
            .filter(warehouse -> warehouse.getWarehouseId().equals(activity.getWarehouseId()))
            .findFirst();
            
        if (warehouseOpt.isPresent()) {
            Warehouse warehouse = warehouseOpt.get();
            
            switch (activity.getAction()) {
                case PAYLOAD_DELIVERED -> warehouse.addCapacity(activity.getAmount());
                case LOADING_VESSEL -> warehouse.addCapacity(-activity.getAmount());
                default -> throw new IllegalArgumentException("Unexpected value: " + activity.getAction());
            }
            
            log.info("Updated warehouse {} capacity to {} tons (not saved to avoid duplicates)", 
            warehouse.getWarehouseId(), warehouse.getCurrentCapacity());
        }
    }
    
} 