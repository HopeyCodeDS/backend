package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.TruckOperationResponseDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.CreateTruckRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.UpdateTruckRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.TruckOperationMapper;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import be.kdg.prog6.landsideContext.domain.commands.CreateTruckCommand;
import be.kdg.prog6.landsideContext.domain.commands.UpdateTruckCommand;
import be.kdg.prog6.landsideContext.ports.in.*;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.WeighbridgeTicketRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/landside/trucks")
@RequiredArgsConstructor
@Slf4j
public class TruckOperationController {
    
    private final GetAllTrucksUseCase getAllTrucksUseCase;
    private final CreateTruckUseCase createTruckUseCase;
    private final UpdateTruckUseCase updateTruckUseCase;
    private final DeleteTruckUseCase deleteTruckUseCase;
    private final TruckOperationMapper truckOperationMapper;
    
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final TruckMovementRepositoryPort truckMovementRepositoryPort;
    private final WeighbridgeTicketRepositoryPort weighbridgeTicketRepositoryPort;
    
    @GetMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER') or hasRole('TRUCK_DRIVER')")
    public ResponseEntity<List<TruckOperationResponseDto>> getAllTrucks() {
        try {
            log.info("Retrieving all trucks");
            
            List<Truck> trucks = getAllTrucksUseCase.getAllTrucks();
            
            // Convert to response DTOs with truck data
            List<TruckOperationResponseDto> responseDtos = trucks.stream()
                .map(this::enrichTruckData)
                .collect(Collectors.toList());
            
            log.info("Successfully retrieved {} trucks", responseDtos.size());
            return ResponseEntity.ok(responseDtos);
            
        } catch (Exception e) {
            log.error("Error retrieving trucks: {}", e.getMessage(), e);
            return ResponseEntity.ok(List.of()); 
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER') or hasRole('TRUCK_DRIVER')")
    public ResponseEntity<?> getTruckById(@PathVariable UUID id) {
        try {
            log.info("Retrieving truck by ID: {}", id);
            
            Optional<Truck> truckOpt = getAllTrucksUseCase.getTruckById(id);
            
            if (truckOpt.isEmpty()) {
                log.warn("Truck not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            Truck truck = truckOpt.get();
            TruckOperationResponseDto responseDto = enrichTruckData(truck);
            
            log.info("Successfully retrieved truck: {}", truck.getLicensePlate().getValue());
            return ResponseEntity.ok(responseDto);
            
        } catch (Exception e) {
            log.error("Error retrieving truck with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Internal server error",
                "message", "An unexpected error occurred while retrieving truck"
            ));
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<?> createTruck(@RequestBody CreateTruckRequestDto requestDto) {
        try {
            log.info("Creating new truck with license plate: {}", requestDto.licensePlate());
            
            CreateTruckCommand command = truckOperationMapper.toCreateCommand(requestDto);
            Truck createdTruck = createTruckUseCase.createTruck(command);
            
            TruckOperationResponseDto responseDto = enrichTruckData(createdTruck);
            
            log.info("Successfully created truck with ID: {}", createdTruck.getTruckId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
            
        } catch (IllegalArgumentException e) {
            log.warn("Invalid request for creating truck: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Bad request",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            log.error("Error creating truck: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Internal server error",
                "message", "An unexpected error occurred while creating truck"
            ));
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<?> updateTruck(@PathVariable UUID id, @RequestBody UpdateTruckRequestDto requestDto) {
        try {
            log.info("Updating truck with ID: {}", id);
            
            UpdateTruckCommand command = truckOperationMapper.toUpdateCommand(requestDto);
            Truck updatedTruck = updateTruckUseCase.updateTruck(id, command);
            
            TruckOperationResponseDto responseDto = enrichTruckData(updatedTruck);
            
            log.info("Successfully updated truck with ID: {}", id);
            return ResponseEntity.ok(responseDto);
            
        } catch (IllegalArgumentException e) {
            log.warn("Invalid request for updating truck: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Bad request",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            log.error("Error updating truck with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Internal server error",
                "message", "An unexpected error occurred while updating truck"
            ));
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<?> deleteTruck(@PathVariable UUID id) {
        try {
            log.info("Deleting truck with ID: {}", id);
            
            deleteTruckUseCase.deleteTruck(id);
            
            log.info("Successfully deleted truck with ID: {}", id);
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            log.warn("Invalid request for deleting truck: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Bad request",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            log.error("Error deleting truck with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Internal server error",
                "message", "An unexpected error occurred while deleting truck"
            ));
        }
    }
    
    /**
     * Enriches truck data with related information from appointments, movements, and weighbridge tickets
     */
    private TruckOperationResponseDto enrichTruckData(Truck truck) {
        String licensePlate = truck.getLicensePlate().getValue();
        
        // Find related appointment
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate)
            .stream()
            .findFirst();
        
        // Find related truck movement
        Optional<TruckMovement> movementOpt = truckMovementRepositoryPort.findByLicensePlate(licensePlate);
        
        // Find related weighbridge ticket (most recent)
        Optional<WeighbridgeTicket> ticketOpt = weighbridgeTicketRepositoryPort.findByLicensePlate(licensePlate)
            .stream()
            .findFirst();
        
        return truckOperationMapper.toResponseDto(truck, appointmentOpt, movementOpt, ticketOpt);
    }
}
