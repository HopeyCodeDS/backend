package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivityAction;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivityWindow;
import be.kdg.prog6.warehousingContext.domain.commands.DeliverPayloadCommand;
import be.kdg.prog6.warehousingContext.ports.in.DeliverPayloadUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PDTGeneratedPort;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseActivityRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import be.kdg.prog6.warehousingContext.domain.Warehouse;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliverPayloadUseCaseImpl implements DeliverPayloadUseCase {
    
    private final PDTRepositoryPort pdtRepositoryPort;
    private final PDTGeneratedPort pdtGeneratedPort;
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    private final WarehouseActivityRepositoryPort warehouseActivityRepositoryPort;

    @Override
    @Transactional
    public PayloadDeliveryTicket deliverPayload(DeliverPayloadCommand command) {
        // Validate command
        validateCommand(command);
        
        // Assign conveyor belt based on material type
        String assignedConveyorBelt = assignConveyorBelt(command.rawMaterialName());
        log.info("Assigned conveyor belt: {} for material: {}", assignedConveyorBelt, command.rawMaterialName());
        
        // Simulate pressure sensor activation and conveyor belt start
        log.info("Pressure sensor activated for conveyor belt: {}", assignedConveyorBelt);
        log.info("Starting conveyor belt: {}", assignedConveyorBelt);

        
        // EVENT SOURCING: Create warehouse activity instead of direct update
        UUID warehouseId = findWarehouseId(command.warehouseNumber());
        validateWarehouseCapacity(warehouseId, command.payloadWeight(), command.rawMaterialName());

        // DEBUG: Log the warehouse ID being used
        log.info("Using warehouse ID: {} for warehouse number: {}", warehouseId, command.warehouseNumber());

        // Create warehouse activity
        WarehouseActivity activity = new WarehouseActivity(
            warehouseId,
            command.payloadWeight(),
            WarehouseActivityAction.MATERIAL_DELIVERED,
            command.rawMaterialName(),
            command.licensePlate(),
            String.format("Material delivered by truck %s", command.licensePlate())
        );
        
        // DEBUG: Log the activity details
        log.info("Created warehouse activity: ID={}, WarehouseID={}, Amount={}, Action={}", 
            activity.getActivityId(), activity.getWarehouseId(), activity.getAmount(), activity.getAction());
        
        // Save activity to event store
        warehouseActivityRepositoryPort.save(activity);

        // Update read model (warehouses.current_capacity)
        updateWarehouseReadModel(warehouseId, activity);
        
        // Generate new weighing bridge number
        String newWeighingBridgeNumber = generateNewWeighingBridgeNumber();
        log.info("Generated new weighing bridge number: {} for truck: {}", newWeighingBridgeNumber, command.licensePlate());

        
        // Generate PDT (Payload Delivery Ticket)
        PayloadDeliveryTicket pdt = new PayloadDeliveryTicket(
            UUID.randomUUID(),
            command.licensePlate(),
            command.rawMaterialName(),
            command.warehouseNumber(),
            assignedConveyorBelt,
            command.payloadWeight(),
            command.sellerId(),
            command.deliveryTime(),
            newWeighingBridgeNumber
        );
        
        // Save PDT
        PayloadDeliveryTicket savedPdt = pdtRepositoryPort.save(pdt);
        
        // Publish event
        pdtGeneratedPort.pdtGenerated(savedPdt);
        
        log.info("Payload delivery ticket generated: {} for truck: {}", 
                savedPdt.getPdtId(), command.licensePlate());
        
        return savedPdt;
    }


    // conveyor belt assignment method
    private String assignConveyorBelt(String rawMaterialName) {
        return switch (rawMaterialName.toLowerCase()) {
            case "gypsum" -> "Conveyor-1";
            case "iron ore" -> "Conveyor-2";
            case "cement" -> "Conveyor-3";
            case "petcoke" -> "Conveyor-4";
            case "slag" -> "Conveyor-5";
            default -> "Conveyor-General";
        };
    }

    private UUID findWarehouseId(String warehouseNumber) {
        log.info("Looking up warehouse ID for warehouse number: {}", warehouseNumber);
        
        // First, let's check if the warehouse exists in the database
        Optional<Warehouse> warehouseOpt = warehouseRepositoryPort.findByWarehouseNumber(warehouseNumber);
        
        if (warehouseOpt.isEmpty()) {
            log.error("Warehouse not found for warehouse number: {}", warehouseNumber);
            
            // Let's try to find all warehouses to see what's available
            List<Warehouse> allWarehouses = warehouseRepositoryPort.findAvailableWarehouses("seller-001", "Gypsum");
            log.info("Available warehouses for seller-001 and Gypsum: {}", 
                allWarehouses.stream().map(w -> w.getWarehouseNumber() + ":" + w.getWarehouseId()).toList());
            
            throw new IllegalStateException("Warehouse not found: " + warehouseNumber);
        }
        
        Warehouse warehouse = warehouseOpt.get();
        UUID warehouseId = warehouse.getWarehouseId();
        
        log.info("Found warehouse ID: {} for warehouse number: {}", warehouseId, warehouseNumber);
        log.info("Warehouse details: ID={}, Number={}, Seller={}, Material={}", 
            warehouseId, warehouse.getWarehouseNumber(), warehouse.getSellerId(), 
            warehouse.getAssignedMaterial().getName());
        
        return warehouseId;
    }

    private void validateWarehouseCapacity(UUID warehouseId, double payloadWeight, String materialType) {
        // Get warehouse activity window
        List<WarehouseActivity> activities = warehouseActivityRepositoryPort.findByWarehouseId(warehouseId);
        WarehouseActivityWindow activityWindow = new WarehouseActivityWindow();
        activities.forEach(activityWindow::addActivity);
        
        // Check capacity
        double maxCapacity = 500_000.0; // 500 kt
        if (!activityWindow.hasAvailableCapacity(payloadWeight, maxCapacity)) {
            throw new IllegalStateException(
                String.format("Warehouse %s does not have sufficient capacity. Current: %.2f tons, Required: %.2f tons", 
                    warehouseId, activityWindow.getCurrentCapacity(), payloadWeight)
            );
        }
        
        log.info("Warehouse {} capacity validated. Current: {} tons, Adding: {} tons", 
            warehouseId, activityWindow.getCurrentCapacity(), payloadWeight);
    }
    
    private void validateCommand(DeliverPayloadCommand command) {
        if (command.licensePlate() == null || command.licensePlate().trim().isEmpty()) {
            throw new IllegalArgumentException("License plate is required");
        }
        if (command.rawMaterialName() == null || command.rawMaterialName().trim().isEmpty()) {
            throw new IllegalArgumentException("Raw material name is required");
        }
        if (command.warehouseNumber() == null || command.warehouseNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse number is required");
        }
        if (command.payloadWeight() <= 0) {
            throw new IllegalArgumentException("Payload weight must be greater than 0");
        }
    }

    private String generateNewWeighingBridgeNumber() {
        // Simple logic: generate a new bridge number
        return "WB-" + (int)(Math.random() * 10 + 1);
    }

    // Update read model
private void updateWarehouseReadModel(UUID warehouseId, WarehouseActivity activity) {
    try {
        Optional<Warehouse> warehouseOpt = warehouseRepositoryPort.findById(warehouseId);
        if (warehouseOpt.isPresent()) {
            Warehouse warehouse = warehouseOpt.get();
            
            // Calculate new capacity based on activity
            double capacityChange = 0.0;
            switch (activity.getAction()) {
                case MATERIAL_DELIVERED:
                    capacityChange = activity.getAmount();
                    break;
                case MATERIAL_SHIPPED:
                    capacityChange = -activity.getAmount();
                    break;
                case CAPACITY_ADJUSTMENT:
                    capacityChange = activity.getAmount();
                    break;
            }
            
            // Update warehouse capacity
            double newCapacity = warehouse.getCurrentCapacity() + capacityChange;
            warehouse.setCurrentCapacity(newCapacity);
            
            // Save updated warehouse
            warehouseRepositoryPort.save(warehouse);
            
            log.info("Updated warehouse {} read model: capacity changed by {} to {}", 
                warehouseId, capacityChange, newCapacity);
        }
    } catch (Exception e) {
        log.error("Failed to update warehouse read model for warehouse {}: {}", warehouseId, e.getMessage());
    }
}
} 