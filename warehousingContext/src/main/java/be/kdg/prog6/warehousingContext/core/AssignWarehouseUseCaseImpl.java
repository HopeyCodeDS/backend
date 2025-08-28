package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.RawMaterial;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.domain.WarehouseAssignment;
import be.kdg.prog6.warehousingContext.domain.commands.AssignWarehouseCommand;
import be.kdg.prog6.common.events.WarehouseAssigned;
import be.kdg.prog6.warehousingContext.ports.in.AssignWarehouseUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseAssignedPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseAssignmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignWarehouseUseCaseImpl implements AssignWarehouseUseCase {
    
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    private final WarehouseAssignedPort warehouseAssignedPort;
    private final WarehouseAssignmentRepositoryPort warehouseAssignmentRepositoryPort;

    @Override
    @Transactional
    public String assignWarehouse(AssignWarehouseCommand command) {
        // Validate input
        validateCommand(command);
        
        // Create domain objects
        RawMaterial rawMaterial = RawMaterial.fromName(command.rawMaterialName());
        
        // Find available warehouses for this seller and material
        List<Warehouse> availableWarehouses = warehouseRepositoryPort.findAvailableWarehouses(
            command.sellerId(), 
            rawMaterial.getName()
        );
        
        if (availableWarehouses.isEmpty()) {
            throw new IllegalStateException("No available warehouses for seller: " + command.sellerId() + 
                                          " and material: " + command.rawMaterialName());
        }
        
        // Find the warehouse with the most available capacity (oldest material first strategy)
        Warehouse selectedWarehouse = availableWarehouses.stream()
            .filter(Warehouse::isAcceptingNewDeliveries)
            .min((w1, w2) -> Double.compare(w1.getCurrentCapacity(), w2.getCurrentCapacity()))
            .orElseThrow(() -> new IllegalStateException("No warehouses accepting new deliveries"));

        // Add delivery to warehouse
        warehouseRepositoryPort.save(selectedWarehouse);

        // Saving warehouse assignment record
        WarehouseAssignment assignment = new WarehouseAssignment(
            UUID.randomUUID(),
            selectedWarehouse.getWarehouseId(),
            command.licensePlate(),
            selectedWarehouse.getWarehouseNumber(),
            command.rawMaterialName(),
            command.sellerId(),
            command.truckWeight(),
            LocalDateTime.now()
        );

        warehouseAssignmentRepositoryPort.save(assignment);
        log.info("Warehouse assignment saved: {}", assignment);
        // Publishing event to notify Landside Context
        warehouseAssignedPort.warehouseAssigned(
            new WarehouseAssigned(
                selectedWarehouse.getWarehouseId(),
                command.licensePlate(),
                selectedWarehouse.getWarehouseNumber(),
                command.rawMaterialName(),
                command.sellerId(),
                command.truckWeight()
            )
        );
        log.info("Warehouse assigned: {}", selectedWarehouse.getWarehouseNumber());
        return selectedWarehouse.getWarehouseNumber();

    }
    
    private void validateCommand(AssignWarehouseCommand command) {
        if (command.licensePlate() == null) {
            throw new IllegalArgumentException("License plate is required");
        }
        if (command.rawMaterialName() == null) {
            throw new IllegalArgumentException("Raw material name is required");
        }
        if (command.sellerId() == null) {
            throw new IllegalArgumentException("Seller ID is required");
        }
        if (command.truckWeight() <= 0) {
            throw new IllegalArgumentException("Truck weight must be greater than 0");
        }
    }
} 