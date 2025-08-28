package be.kdg.prog6.warehousingContext.adapters.in.web;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.*;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.OldestStockAllocationMapper;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.WarehouseMapper;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.WarehouseOverviewMapper;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.WarehouseResponseMapper;
import be.kdg.prog6.warehousingContext.domain.commands.AssignWarehouseCommand;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.ports.in.*;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import be.kdg.prog6.warehousingContext.domain.commands.CreateWarehouseCommand;

@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
@Slf4j
public class WarehouseController {
    
    private final AssignWarehouseUseCase assignWarehouseUseCase;
    private final GetWarehouseOverviewUseCase getWarehouseOverviewUseCase;
    private final GetWarehouseByIdUseCase getWarehouseByIdUseCase;
    private final GetWarehousesBySellerUseCase getWarehousesBySellerUseCase;
    private final CreateWarehouseUseCase createWarehouseUseCase;
    private final DeleteWarehouseUseCase deleteWarehouseUseCase;
    private final AllocateOldestStockUseCase allocateOldestStockUseCase;
    
    private final WarehouseMapper warehouseMapper;
    private final WarehouseOverviewMapper warehouseOverviewMapper;
    private final WarehouseResponseMapper warehouseResponseMapper;
    private final OldestStockAllocationMapper oldestStockAllocationMapper;

    private final PDTRepositoryPort pdtRepositoryPort;

    // GET /warehouses 
    @GetMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<WarehouseResponseDto>> getAllWarehouses() {
        try {
            log.info("Getting all warehouses");
            var warehouses = getWarehouseOverviewUseCase.getAllWarehouses();
            
            List<WarehouseResponseDto> response = warehouses.stream()
            .map(warehouse -> {
                log.info("Processing warehouse: {} with number: '{}'", 
                    warehouse.getWarehouseId(), warehouse.getWarehouseNumber());
                
                // Fetching actual payloads for each warehouse
                var payloads = pdtRepositoryPort.findByWarehouseNumber(warehouse.getWarehouseNumber());

                log.info("Found {} payloads for warehouse '{}'", 
                    payloads.size(), warehouse.getWarehouseNumber());
                
                if (payloads.isEmpty()) {
                    log.warn("No payloads found for warehouse '{}'. This might indicate a data mismatch.", 
                        warehouse.getWarehouseNumber());
                }
                
                return warehouseResponseMapper.toWarehouseResponseDto(warehouse, payloads);
            })
            .collect(java.util.stream.Collectors.toList());
            
            log.info("Successfully retrieved {} warehouses", response.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving all warehouses: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /warehouses/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<WarehouseResponseDto> getWarehouseById(@PathVariable UUID id) {
        try {
            log.info("Getting warehouse by ID: {}", id);
            var warehouse = getWarehouseByIdUseCase.getWarehouseById(id);
            
            if (warehouse.isPresent()) {
                // Get payloads from the PDT repository
                List<PayloadDeliveryTicket> pdtList = pdtRepositoryPort.findByWarehouseNumber(warehouse.get().getWarehouseNumber());
                var response = warehouseResponseMapper.toWarehouseResponseDto(warehouse.get(), pdtList);
                log.info("Successfully retrieved warehouse: {}", id);
                return ResponseEntity.ok(response);
            } else {
                log.warn("Warehouse not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error retrieving warehouse by ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /warehouses/overview 
    @GetMapping("/overview")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<WarehouseOverviewDto> getWarehouseOverview() {
        log.info("Getting warehouse overview for warehouse manager");

        var warehouses = getWarehouseOverviewUseCase.getAllWarehouses();
        log.info("Retrieved {} warehouses from database", warehouses.size());

        var totalRawMaterial = getWarehouseOverviewUseCase.getTotalRawMaterialInWarehouses();
        log.info("Total raw material in warehouses: {} tons", totalRawMaterial);

        WarehouseOverviewDto overview = warehouseOverviewMapper.toWarehouseOverviewDto(warehouses, totalRawMaterial);
        log.info("Successfully generated warehouse overview with {} warehouse details", overview.getWarehouses().size());
        
        return ResponseEntity.ok(overview);
    }

    // GET /warehouses/by-seller/{sellerId} 
    @GetMapping("/by-seller/{sellerId}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<WarehouseResponseDto>> getWarehousesBySeller(@PathVariable UUID sellerId) {
        try {
            log.info("Getting warehouses by seller: {}", sellerId);
            var warehouses = getWarehousesBySellerUseCase.getWarehousesBySeller(sellerId);
            
            List<WarehouseResponseDto> response = warehouses.stream()
            .map(warehouse -> {
                // Fetching actual payloads for each warehouse
                var payloads = pdtRepositoryPort.findByWarehouseNumber(warehouse.getWarehouseNumber());
                return warehouseResponseMapper.toWarehouseResponseDto(warehouse, payloads);
            })
            .collect(java.util.stream.Collectors.toList());
            
            log.info("Successfully retrieved {} warehouses for seller: {}", response.size(), sellerId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving warehouses by seller {}: {}", sellerId, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /warehouses 
    @PostMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<WarehouseResponseDto> createWarehouse(@RequestBody WarehouseCreateRequestDto requestDto) {
        // Convert DTO to domain command
        CreateWarehouseCommand command = new CreateWarehouseCommand(
            requestDto.getWarehouseNumber(),
            requestDto.getSellerId(),
            requestDto.getRawMaterialName(),
            requestDto.getMaxCapacity()
        );
        
        // Execute use case
        Warehouse warehouse = createWarehouseUseCase.createWarehouse(command);
        
        return ResponseEntity.ok(warehouseResponseMapper.toWarehouseResponseDto(warehouse, List.of()));
    }

    // DELETE /warehouses/{id} - Delete warehouse
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable UUID id) {
        try {
            log.info("Deleting warehouse: {}", id);
            deleteWarehouseUseCase.deleteWarehouse(id);
            log.info("Successfully deleted warehouse: {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Validation error deleting warehouse {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error deleting warehouse {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // Assign Warehouse
    @PostMapping("/assign")
    public ResponseEntity<Map<String, Object>> assignWarehouse(@RequestBody AssignWarehouseRequestDto requestDto) {
        try {
            log.debug("Received warehouse assignment request for license plate: {}, raw material: {}", 
                requestDto.getLicensePlate(), requestDto.getRawMaterialName());
            
            AssignWarehouseCommand command = warehouseMapper.toAssignWarehouseCommand(requestDto);
            String warehouseNumber = assignWarehouseUseCase.assignWarehouse(command);
            
            log.info("Successfully assigned warehouse {} to truck {} for material {}", 
                warehouseNumber, requestDto.getLicensePlate(), requestDto.getRawMaterialName());
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Warehouse assigned successfully",
                "warehouseNumber", warehouseNumber,
                "licensePlate", requestDto.getLicensePlate(),
                "rawMaterialName", requestDto.getRawMaterialName()
            ));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", "Validation error",
                "message", e.getMessage()
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", "Assignment failed",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "error", "Internal server error",
                "message", "An unexpected error occurred"
            ));
        }
    }

    @PostMapping("/allocate-oldest-stock")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')") 
    public ResponseEntity<OldestStockAllocationDto> allocateOldestStockForLoading(
            @RequestParam String rawMaterialName, 
            @RequestParam double requiredAmount) {
        try {
            log.info("Warehouse manager requesting oldest stock allocation for material: {} with amount: {} tons", 
                rawMaterialName, requiredAmount);
            
            List<PayloadDeliveryTicket> allocatedStock = allocateOldestStockUseCase
                .allocateOldestStockForLoading(rawMaterialName, requiredAmount);
            
            OldestStockAllocationDto response = oldestStockAllocationMapper
                .toOldestStockAllocationDto(rawMaterialName, requiredAmount, allocatedStock);
            
            log.info("Successfully allocated {} PDTs for material: {} with total weight: {} tons", 
                allocatedStock.size(), rawMaterialName, response.getAllocatedAmount());
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalStateException e) {
            log.error("Failed to allocate oldest stock: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Unexpected error during oldest stock allocation: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}