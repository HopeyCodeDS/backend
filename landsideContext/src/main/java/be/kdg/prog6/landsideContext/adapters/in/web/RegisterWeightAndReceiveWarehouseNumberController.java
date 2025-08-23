package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.RegisterWeightRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.RegisterWeightResponseDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.WeightRegistrationMapper;
import be.kdg.prog6.landsideContext.domain.commands.RegisterWeightAndExitBridgeCommand;
import be.kdg.prog6.landsideContext.ports.in.GetWarehouseStatusUseCase;
import be.kdg.prog6.landsideContext.ports.in.RegisterWeightAndExitBridgeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;

@RestController
@RequestMapping("/landside/weight-registration")
@RequiredArgsConstructor
public class RegisterWeightAndReceiveWarehouseNumberController {
    
    private final RegisterWeightAndExitBridgeUseCase registerWeightAndExitBridgeUseCase;
    private final WeightRegistrationMapper mapper;
    //private final TruckMovementRepositoryPort truckMovementRepository;
    private final GetWarehouseStatusUseCase getWarehouseStatusUseCase;
    
    @PostMapping("/register")
    @PreAuthorize("hasRole('TRUCK_DRIVER')")
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
//    @PreAuthorize("hasRole('TRUCK_DRIVER')")
    public ResponseEntity<RegisterWeightResponseDto> getWarehouseStatus(@PathVariable String licensePlate) {
        try {
            var warehouseStatus = getWarehouseStatusUseCase.getWarehouseStatus(licensePlate);
            
            RegisterWeightResponseDto response = new RegisterWeightResponseDto(
                licensePlate,
                warehouseStatus.status(),
                warehouseStatus.message(),
                warehouseStatus.assignedWarehouse()
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