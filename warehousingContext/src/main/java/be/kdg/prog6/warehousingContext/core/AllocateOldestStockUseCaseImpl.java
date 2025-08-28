package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.common.events.ShipLoadedEvent;
import be.kdg.prog6.common.events.WarehouseActivityEvent;
import be.kdg.prog6.warehousingContext.domain.OldestStockAllocationStrategy;
import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivity;
import be.kdg.prog6.warehousingContext.domain.WarehouseActivityAction;
import be.kdg.prog6.warehousingContext.domain.PurchaseOrderMaterialRequirement;
import be.kdg.prog6.warehousingContext.ports.in.AllocateOldestStockUseCase;
import be.kdg.prog6.warehousingContext.ports.in.ProjectWarehouseActivityUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderFulfillmentRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderMaterialRequirementRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.ShipLoadedEventPublisherPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseActivityEventPublisherPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseActivityRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllocateOldestStockUseCaseImpl implements AllocateOldestStockUseCase {
    
    private final PDTRepositoryPort pdtRepositoryPort;
    private final PurchaseOrderFulfillmentRepositoryPort purchaseOrderFulfillmentRepositoryPort;
    private final PurchaseOrderMaterialRequirementRepositoryPort materialRequirementRepositoryPort;
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    private final WarehouseActivityRepositoryPort warehouseActivityRepositoryPort;
    private final ProjectWarehouseActivityUseCase projectWarehouseActivityUseCase;
    private final ShipLoadedEventPublisherPort shipLoadedEventPublisherPort;
    private final WarehouseActivityEventPublisherPort warehouseActivityEventPublisherPort;

    @Override
    public List<PayloadDeliveryTicket> allocateOldestStockForLoading(String rawMaterialName, double requiredAmount) {
        log.info("Warehouse manager requesting oldest stock allocation for material: {} with amount: {} tons", 
            rawMaterialName, requiredAmount);
        
        // Get all PDTs for the specified material, ordered by delivery time (oldest first)
        List<PayloadDeliveryTicket> availableStock = pdtRepositoryPort
            .findByRawMaterialOrderByDeliveryTimeAsc(rawMaterialName);
        
        log.info("Found {} PDTs for material: {}", availableStock.size(), rawMaterialName);
        log.info("PDTs: {}", availableStock.stream().map(PayloadDeliveryTicket::getDeliveryTime).collect(Collectors.toList()));
        
        // Calculate total available stock using domain logic
        double totalAvailableStock = OldestStockAllocationStrategy.calculateTotalAvailableStock(availableStock);
        
        if (totalAvailableStock < requiredAmount) {
            throw new IllegalStateException(
                String.format("Insufficient stock for material %s. Required: %.2f tons, Available: %.2f tons", 
                    rawMaterialName, requiredAmount, totalAvailableStock)
            );
        }
        
        // Apply domain strategy for FIFO allocation
        List<PayloadDeliveryTicket> allocatedStock = OldestStockAllocationStrategy
            .allocateOldestStockFIFO(availableStock, requiredAmount);
        
        log.info("Successfully allocated {} PDTs for material: {} with total weight: {} tons", 
            allocatedStock.size(), rawMaterialName, 
            allocatedStock.stream().mapToDouble(PayloadDeliveryTicket::getPayloadWeight).sum());
        
        return allocatedStock;
    }

    @Override
    @Transactional
    public void allocateAndDeductOldestStockForShippingOrder(UUID shippingOrderId, String purchaseOrderReference) {
        log.info("Automatically allocating and deducting oldest stock for shipping order: {} with PO: {}", 
            shippingOrderId, purchaseOrderReference);
        
        // Get PO fulfillment tracking to get vessel info
        Optional<PurchaseOrderFulfillmentTracking> fulfillmentOpt = 
            purchaseOrderFulfillmentRepositoryPort.findByPurchaseOrderNumber(purchaseOrderReference);
        
        if (fulfillmentOpt.isEmpty()) {
            log.warn("No purchase order fulfillment tracking found for PO: {}", purchaseOrderReference);
            return;
        }
        
        PurchaseOrderFulfillmentTracking fulfillment = fulfillmentOpt.get();
        String vesselNumber = fulfillment.getVesselNumber();
        
        if (vesselNumber == null) {
            log.warn("Vessel number not set for PO fulfillment: {}", purchaseOrderReference);
            return;
        }
        
        log.info("Found vessel {} for PO: {}", vesselNumber, purchaseOrderReference);

        // Get material requirements for this purchase order
        List<PurchaseOrderMaterialRequirement> materialRequirements = 
            materialRequirementRepositoryPort.findByPurchaseOrderNumber(purchaseOrderReference);
        
        if (materialRequirements.isEmpty()) {
            log.warn("No material requirements found for PO: {}", purchaseOrderReference);
            return;
        }

        log.info("Found {} material requirements for PO: {}", materialRequirements.size(), purchaseOrderReference);
        
        // Process each material requirement
        for (PurchaseOrderMaterialRequirement requirement : materialRequirements) {
            String materialName = requirement.getRawMaterialName();
            double requiredAmount = requirement.getRemainingAmountInTons();
            
            log.info("Processing material requirement for PO {}: {} tons of {}", 
                purchaseOrderReference, requiredAmount, materialName);
            
            if (requiredAmount <= 0) {
                log.info("Material requirement for {} is already fulfilled", materialName);
                continue;
            }
            
            // Get all PDTs for this material, ordered by delivery time (oldest first)
            List<PayloadDeliveryTicket> materialPdts = pdtRepositoryPort
                .findByRawMaterialOrderByDeliveryTimeAsc(materialName);
            
            if (materialPdts.isEmpty()) {
                log.warn("No PDTs found for material: {}", materialName);
                continue;
            }
            
            // Get total available for this material
            double totalAvailable = OldestStockAllocationStrategy.calculateTotalAvailableStock(materialPdts);
            
            if (totalAvailable < requiredAmount) {
                log.warn("Insufficient stock for material {}. Required: {} tons, Available: {} tons", 
                    materialName, requiredAmount, totalAvailable);
                continue;
            }

            // Calculate deduction amounts for each PDT using FIFO strategy
            Map<UUID, Double> deductionAmounts = OldestStockAllocationStrategy
                .calculateDeductionAmounts(materialPdts, requiredAmount);
            
            double totalDeducted = 0.0;

            // Create LOADING_VESSEL activities for each allocated PDT with actual deduction amounts
            for (PayloadDeliveryTicket pdt : materialPdts) {
                Double deductionAmount = deductionAmounts.get(pdt.getPdtId());
                
                if (deductionAmount != null && deductionAmount > 0) {
                    // Get warehouse info once
                    Optional<Warehouse> warehouseOpt = warehouseRepositoryPort.findByWarehouseNumber(pdt.getWarehouseNumber());
                    
                    if (warehouseOpt.isPresent()) {
                        Warehouse warehouse = warehouseOpt.get();

                        System.out.println("=== VESSEL LOADING OPERATION START ===");
                        System.out.println("Vessel Number: " + vesselNumber);
                        System.out.println("Shipping Order ID: " + shippingOrderId);
                        System.out.println("Purchase Order: " + purchaseOrderReference);
                        System.out.println("PDT ID: " + pdt.getPdtId());
                        System.out.println("Warehouse Number: " + pdt.getWarehouseNumber());
                        System.out.println("Material: " + pdt.getRawMaterialName());
                        System.out.println("Deduction Amount: " + deductionAmount + " tons");
                        System.out.println("Original PDT Weight: " + pdt.getPayloadWeight() + " tons");
                        
                        WarehouseActivity activity = new WarehouseActivity(
                            warehouse.getWarehouseId(),
                            deductionAmount,
                            WarehouseActivityAction.LOADING_VESSEL,
                            pdt.getDeliveryTime(),
                            pdt.getRawMaterialName(),
                            vesselNumber, // I am using the vessel from fulfillment tracking
                            String.format("Material loaded for shipping order %s (PO: %s) with vessel %s. Deducted %.2f tons from PDT %s", 
                                shippingOrderId, purchaseOrderReference, vesselNumber, deductionAmount, pdt.getPdtId())
                        );

                        // Call the Warehouse loadVessel method
                        warehouse.loadVessel(deductionAmount, pdt.getRawMaterialName());
                        log.info("Loaded {} tons of {} from warehouse {}", deductionAmount, pdt.getRawMaterialName(), warehouse.getWarehouseId());

                        // Update warehouse
                        warehouseRepositoryPort.save(warehouse);
                        log.info("Saved warehouse {}", warehouse.getWarehouseId());

                        // Save activity to event store
                        warehouseActivityRepositoryPort.save(activity);

                        // Publish warehouse activity event
                        WarehouseActivityEvent warehouseActivityEvent = new WarehouseActivityEvent(
                            activity.getActivityId(),
                            pdt.getSellerId().toString(),
                            pdt.getWarehouseNumber(),
                                WarehouseActivityAction.LOADING_VESSEL.name(),
                            deductionAmount,
                            pdt.getRawMaterialName(),
                            pdt.getDeliveryTime(),
                            vesselNumber
                        );

                        log.info("Published warehouse activity event to the invoicing context to update storage volume(subtracting): {}", warehouseActivityEvent.toString());
                        warehouseActivityEventPublisherPort.publishWarehouseActivityEvent(warehouseActivityEvent);
                        
                        // Project activity to update read model
                        projectWarehouseActivityUseCase.projectWarehouseActivity(activity);
                        
                        totalDeducted += deductionAmount;
                        
                        log.info("Created LOADING_VESSEL activity for PDT: {} with {} tons of {} for vessel {} (deducted from {} tons total)", 
                            pdt.getPdtId(), deductionAmount, pdt.getRawMaterialName(), vesselNumber, pdt.getPayloadWeight());
                        System.out.println("=== VESSEL LOADING OPERATION COMPLETE ===");
                    } else {
                        log.warn("Warehouse not found for warehouse number: {}", pdt.getWarehouseNumber());
                    }
                }
            }
            
            // Update the material requirement with fulfilled amount
            requirement.fulfillAmount(totalDeducted);
            materialRequirementRepositoryPort.save(requirement);
            
            log.info("Successfully allocated and deducted {} tons of {} for shipping order: {} with vessel: {}", 
                totalDeducted, materialName, shippingOrderId, vesselNumber);
        }
        
        // Mark PO as fulfilled after successful loading
        fulfillment.markAsFulfilled(vesselNumber);
        purchaseOrderFulfillmentRepositoryPort.save(fulfillment);
        
        log.info("Automatic stock allocation and deduction completed for shipping order: {} with vessel: {}", 
            shippingOrderId, vesselNumber);

        // Publish ship loaded event
        ShipLoadedEvent event = new ShipLoadedEvent(
            shippingOrderId,
            purchaseOrderReference,
            vesselNumber,
            fulfillment.getCustomerNumber(),
            LocalDateTime.now()
        );
        shipLoadedEventPublisherPort.publishShipLoadedEvent(event);

        log.info("Published ship loaded event for shipping order: {} with vessel: {}", 
            shippingOrderId, vesselNumber);
    }

} 