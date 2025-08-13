package be.kdg.prog6.warehousingContext.adapters.in.web;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.AssignWarehouseRequestDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.dto.OldestStockAllocationDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.dto.WarehouseOverviewDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.OldestStockAllocationMapper;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.WarehouseMapper;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.WarehouseOverviewMapper;
import be.kdg.prog6.warehousingContext.domain.commands.AssignWarehouseCommand;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.in.AssignWarehouseUseCase;
import be.kdg.prog6.warehousingContext.ports.in.GetWarehouseOverviewUseCase;
import be.kdg.prog6.warehousingContext.ports.in.AllocateOldestStockUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
@Slf4j
public class WarehouseController {
    
    private final AssignWarehouseUseCase assignWarehouseUseCase;
    private final GetWarehouseOverviewUseCase getWarehouseOverviewUseCase;
    private final WarehouseMapper warehouseMapper;
    private final WarehouseOverviewMapper warehouseOverviewMapper;
    private final AllocateOldestStockUseCase allocateOldestStockUseCase;
    private final OldestStockAllocationMapper oldestStockAllocationMapper;

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