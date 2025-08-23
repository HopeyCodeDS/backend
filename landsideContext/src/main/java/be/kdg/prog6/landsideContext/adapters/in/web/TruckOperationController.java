package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.TruckOperationResponseDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.CreateTruckRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.UpdateTruckRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.TruckEnrichmentMapper;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.TruckOperationMapper;
import be.kdg.prog6.landsideContext.domain.commands.CreateTruckCommand;
import be.kdg.prog6.landsideContext.domain.commands.UpdateTruckCommand;
import be.kdg.prog6.landsideContext.ports.in.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/landside/trucks")
@RequiredArgsConstructor
@Slf4j
public class TruckOperationController {

    private final DeleteTruckUseCase deleteTruckUseCase;
    private final EnrichTruckDataUseCase enrichTruckDataUseCase;
    private final TruckOperationMapper truckOperationMapper;
    private final TruckEnrichmentMapper truckEnrichmentMapper;
    
    @GetMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER') or hasRole('TRUCK_DRIVER')")
    public ResponseEntity<List<TruckOperationResponseDto>> getAllTrucks() {
        try {
            log.info("Retrieving all trucks");
            
            var enrichedTrucks = enrichTruckDataUseCase.getAllTrucksWithEnrichedData();
            
            // Convert DTOs to mapper
            List<TruckOperationResponseDto> responseDtos = truckEnrichmentMapper.toTruckOperationResponseDtoList(enrichedTrucks);

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
            
            // Get domain object from use case
            var enrichedTruckOpt = enrichTruckDataUseCase.getTruckByIdWithEnrichedData(id);
            
            if (enrichedTruckOpt.isEmpty()) {
                log.warn("Truck not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            // Convert to DTO
            TruckOperationResponseDto responseDto = truckEnrichmentMapper.toTruckOperationResponseDto(enrichedTruckOpt.get());
            
            log.info("Successfully retrieved truck: {}", responseDto.licensePlate());
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
            
            // Convert to command
            CreateTruckCommand command = truckOperationMapper.toCreateCommand(requestDto);
            
            // Get domain object from use case
            var createdTruck = enrichTruckDataUseCase.createTruckWithEnrichedData(command);
            
            // Convert to DTO
            TruckOperationResponseDto responseDto = truckEnrichmentMapper.toTruckOperationResponseDto(
                enrichTruckDataUseCase.enrichTruckData(createdTruck)
            );
            
            log.info("Successfully created truck with ID: {}", responseDto.licensePlate());
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
            
            // Convert to command
            UpdateTruckCommand command = truckOperationMapper.toUpdateCommand(requestDto);
            
            // Update truck
            var updatedTruck = enrichTruckDataUseCase.updateTruckWithEnrichedData(id, command);
            
            // Convert to DTO
            TruckOperationResponseDto responseDto = truckEnrichmentMapper.toTruckOperationResponseDto(
                enrichTruckDataUseCase.enrichTruckData(updatedTruck)
            );
            
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
}