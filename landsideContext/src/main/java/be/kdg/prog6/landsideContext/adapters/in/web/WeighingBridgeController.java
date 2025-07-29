package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.AssignWeighingBridgeRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.WeighingBridgeMapper;
import be.kdg.prog6.landsideContext.domain.commands.AssignWeighingBridgeCommand;
import be.kdg.prog6.landsideContext.ports.in.AssignWeighingBridgeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/landside/weighing-bridge")
@RequiredArgsConstructor
public class WeighingBridgeController {
    
    private final AssignWeighingBridgeUseCase assignWeighingBridgeUseCase;
    private final WeighingBridgeMapper weighingBridgeMapper;
    
    @PostMapping("/assign")
    public ResponseEntity<Map<String, Object>> assignWeighingBridge(@RequestBody AssignWeighingBridgeRequestDto requestDto) {
        try {
            AssignWeighingBridgeCommand command = weighingBridgeMapper.toAssignWeighingBridgeCommand(requestDto);
            String bridgeNumber = assignWeighingBridgeUseCase.assignWeighingBridge(command);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Weighing bridge assigned successfully",
                "bridgeNumber", bridgeNumber,
                "licensePlate", requestDto.getLicensePlate()
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