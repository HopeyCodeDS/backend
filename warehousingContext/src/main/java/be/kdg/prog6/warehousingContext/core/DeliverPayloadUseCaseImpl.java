package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.common.events.WarehouseActivityEvent;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.domain.ConveyorBeltAssignmentService;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivityAction;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivityWindow;
import be.kdg.prog6.warehousingContext.domain.commands.DeliverPayloadCommand;
import be.kdg.prog6.warehousingContext.ports.in.DeliverPayloadUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PDTGeneratedPort;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseActivityEventPublisherPort;
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
import be.kdg.prog6.warehousingContext.ports.in.ProjectWarehouseActivityUseCase;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliverPayloadUseCaseImpl implements DeliverPayloadUseCase {
    
    private final PDTRepositoryPort pdtRepositoryPort;
    private final PDTGeneratedPort pdtGeneratedPort;
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    private final WarehouseActivityRepositoryPort warehouseActivityRepositoryPort;
    private final ProjectWarehouseActivityUseCase projectWarehouseActivityUseCase;
    private final ConveyorBeltAssignmentService conveyorBeltAssignmentService;
    private final WarehouseActivityEventPublisherPort warehouseActivityEventPublisherPort;

    @Override
    @Transactional
    public PayloadDeliveryTicket deliverPayload(DeliverPayloadCommand command) {
        log.info("Starting payload delivery for truck: {} with {} tons of {}", 
            command.licensePlate(), command.payloadWeight(), command.rawMaterialName());
        System.out.println("=== MATERIAL DELIVERY OPERATION START ===");
        System.out.printf("Truck License Plate: %s\n", command.licensePlate());
        System.out.printf("Payload Weight: %s tons\n", command.payloadWeight());
        System.out.printf("Raw Material: %s\n", command.rawMaterialName());
        System.out.printf("Warehouse Number: %s\n", command.warehouseNumber());
        System.out.printf("Seller ID: %s\n", command.sellerId());
        System.out.printf("Delivery Time: %s\n", command.deliveryTime());


        // 1. Validate command
        validateCommand(command);

        // 2. Find and validate warehouse
        UUID warehouseId = findWarehouseId(command.warehouseNumber());
        validateWarehouseCapacity(warehouseId, command.payloadWeight(), command.rawMaterialName());
        
        // 3. Assign conveyor belt based on material type
        String assignedConveyorBelt = conveyorBeltAssignmentService.assignConveyorBelt(command.rawMaterialName());
        log.info("Assigned conveyor belt: {} for material: {}", assignedConveyorBelt, command.rawMaterialName());
        
        // 4. Simulating pressure sensor activation and conveyor belt start
        log.info("Pressure sensor activated for conveyor belt: {}", assignedConveyorBelt);
        log.info("Starting conveyor belt: {}", assignedConveyorBelt);
        

        // Log the warehouse ID being used
        log.info("Using warehouse ID: {} for warehouse number: {}", warehouseId, command.warehouseNumber());

        // 5. Creating warehouse activity (event sourcing)
        WarehouseActivity activity = new WarehouseActivity(
            warehouseId,
            command.payloadWeight(),
            WarehouseActivityAction.PAYLOAD_DELIVERED,
            command.deliveryTime(),
            command.rawMaterialName(),
            command.licensePlate(),
            String.format("Material delivered by truck %s", command.licensePlate())
        );
        
        log.info("Created warehouse activity: ID={}, WarehouseID={}, Amount={}, Action={}", 
            activity.getActivityId(), activity.getWarehouseId(), activity.getAmount(), activity.getAction());
        
        // 6. Saving activity to event store (event sourcing)
        warehouseActivityRepositoryPort.save(activity);

        // Publish warehouse activity event
        WarehouseActivityEvent warehouseActivityEvent = new WarehouseActivityEvent(
            activity.getActivityId(),
            command.sellerId(),
            command.warehouseNumber(),
            WarehouseActivityAction.PAYLOAD_DELIVERED.name().toString(),
            command.payloadWeight(),
            command.rawMaterialName(),
            activity.getPointInTime(),
            activity.getLicensePlate()
        );

        log.info("Published warehouse activity event to the invoicing context to update storage volume(adding): {}", warehouseActivityEvent.toString());
        warehouseActivityEventPublisherPort.publishWarehouseActivityEvent(warehouseActivityEvent);

        // 7. Projecting warehouse activity to update read model
        projectWarehouseActivityUseCase.projectWarehouseActivity(activity);
        
        // 8. Generate new weighing bridge number
        String newWeighingBridgeNumber = generateNewWeighingBridgeNumber();
        log.info("Generated new weighing bridge number: {} for truck: {}", newWeighingBridgeNumber, command.licensePlate());

        
        // 9. Generate PDT (Payload Delivery Ticket)
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
        
        // 10. Save PDT
        PayloadDeliveryTicket savedPdt = pdtRepositoryPort.save(pdt);
        
        // 11. Publishing event for other contexts
        pdtGeneratedPort.pdtGenerated(savedPdt);
        
        log.info("Payload delivery ticket generated: {} for truck: {}", 
                savedPdt.getPdtId(), command.licensePlate());
        log.info("=== MATERIAL DELIVERY OPERATION COMPLETE ===");

        return savedPdt;
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
        if (command.sellerId() == null || command.sellerId().trim().isEmpty()) {
            throw new IllegalArgumentException("Seller ID is required");
        }
    }


    private UUID findWarehouseId(String warehouseNumber) {
        log.info("Looking up warehouse ID for warehouse number: {}", warehouseNumber);
        
        // First, checking if the warehouse exists in the database
        Optional<Warehouse> warehouseOpt = warehouseRepositoryPort.findByWarehouseNumber(warehouseNumber);
        
        if (warehouseOpt.isEmpty()) {
            log.error("Warehouse not found for warehouse number: {}", warehouseNumber);
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
        log.info("Validating warehouse capacity for warehouse: {}, payload: {} tons", warehouseId, payloadWeight);
        
        // Getting warehouse activity window
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

    private String generateNewWeighingBridgeNumber() {
        // Simple logic: generating a new bridge number
        return "WB-" + (int)(Math.random() * 10 + 1);
    }
} 