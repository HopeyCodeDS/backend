package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.RegisterWeightRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.RegisterWeightResponseDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.WeightRegistrationMapper;
import be.kdg.prog6.landsideContext.domain.commands.RegisterWeightAndExitBridgeCommand;
import be.kdg.prog6.landsideContext.ports.in.RegisterWeightAndExitBridgeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;

@RestController
@RequestMapping("/landside/weight-registration")
@RequiredArgsConstructor
public class RegisterWeightAndReceiveWarehouseNumberController {
    
    private final RegisterWeightAndExitBridgeUseCase registerWeightAndExitBridgeUseCase;
    private final WeightRegistrationMapper mapper;
    private final TruckMovementRepositoryPort truckMovementRepository;
    
    @PostMapping("/register")
    public ResponseEntity<RegisterWeightResponseDto> registerWeight(@RequestBody RegisterWeightRequestDto requestDto) {
        RegisterWeightAndExitBridgeCommand command = mapper.toCommand(requestDto);
        registerWeightAndExitBridgeUseCase.registerWeightAndExitBridge(command);
        
        // Return immediate response - warehouse assignment is async
        RegisterWeightResponseDto response = new RegisterWeightResponseDto(
            requestDto.getLicensePlate(),
            "PROCESSING",
            "Weight registered successfully. Warehouse assignment in progress.",
            null
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{licensePlate}")
    public ResponseEntity<RegisterWeightResponseDto> getWarehouseStatus(@PathVariable String licensePlate) {
        try {
            var movement = truckMovementRepository.findByLicensePlate(licensePlate)
                    .orElseThrow(() -> new IllegalArgumentException("Truck movement not found"));

            String status = movement.getAssignedWarehouse() != null ? "COMPLETED" : "PROCESSING";
            String message = movement.getAssignedWarehouse() != null ? 
                    "Warehouse assigned successfully" : 
                    "Warehouse assignment in progress";

            RegisterWeightResponseDto response = new RegisterWeightResponseDto(
                licensePlate,
                status,
                message,
                movement.getAssignedWarehouse()
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RegisterWeightResponseDto(
                licensePlate,
                "ERROR",
                e.getMessage(),
                null
            ));
        }
    }
} 