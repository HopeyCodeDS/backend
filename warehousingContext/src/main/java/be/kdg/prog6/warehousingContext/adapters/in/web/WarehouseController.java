package be.kdg.prog6.warehousingContext.adapters.in.web;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.AssignWarehouseRequestDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.dto.WarehouseOverviewDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.WarehouseMapper;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.WarehouseOverviewMapper;
import be.kdg.prog6.warehousingContext.domain.commands.AssignWarehouseCommand;
import be.kdg.prog6.warehousingContext.ports.in.AssignWarehouseUseCase;
import be.kdg.prog6.warehousingContext.ports.in.GetWarehouseOverviewUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    @GetMapping("/overview")
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
}