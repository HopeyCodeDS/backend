package be.kdg.prog6.warehousingContext.adapters.in.web;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.AssignWarehouseRequestDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.WarehouseMapper;
import be.kdg.prog6.warehousingContext.domain.commands.AssignWarehouseCommand;
import be.kdg.prog6.warehousingContext.ports.in.AssignWarehouseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    
    private final AssignWarehouseUseCase assignWarehouseUseCase;
    private final WarehouseMapper warehouseMapper;
    
    @PostMapping("/assign")
    public ResponseEntity<Map<String, Object>> assignWarehouse(@RequestBody AssignWarehouseRequestDto requestDto) {
        try {
            AssignWarehouseCommand command = warehouseMapper.toAssignWarehouseCommand(requestDto);
            String warehouseNumber = assignWarehouseUseCase.assignWarehouse(command);
            
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