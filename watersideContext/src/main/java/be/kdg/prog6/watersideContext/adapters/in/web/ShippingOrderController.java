package be.kdg.prog6.watersideContext.adapters.in.web;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.CompleteInspectionRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.CompleteBunkeringRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.FullShippingOrderResponseDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.ShippingOrderResponseDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.UpdateShippingOrderRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.SubmitShippingOrderRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.mapper.ShippingOrderMapper;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.CompleteInspectionCommand;
import be.kdg.prog6.watersideContext.domain.commands.CompleteBunkeringCommand;
import be.kdg.prog6.watersideContext.domain.commands.SubmitShippingOrderCommand;
import be.kdg.prog6.watersideContext.domain.commands.UpdateShippingOrderCommand;
import be.kdg.prog6.watersideContext.ports.in.CompleteInspectionUseCase;
import be.kdg.prog6.watersideContext.ports.in.CompleteBunkeringUseCase;
import be.kdg.prog6.watersideContext.ports.in.QueryShippingOrderUseCase;
import be.kdg.prog6.watersideContext.ports.in.SubmitShippingOrderUseCase;
import be.kdg.prog6.watersideContext.ports.in.UpdateShippingOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@RestController
@RequestMapping("/waterside/shipping-orders")
@RequiredArgsConstructor
@Slf4j
public class ShippingOrderController {
    
    private final SubmitShippingOrderUseCase submitShippingOrderUseCase;
    private final ShippingOrderMapper shippingOrderMapper;
    private final QueryShippingOrderUseCase queryShippingOrderUseCase;
    private final UpdateShippingOrderUseCase updateShippingOrderUseCase;
    private final CompleteInspectionUseCase completeInspectionUseCase;
    private final CompleteBunkeringUseCase completeBunkeringUseCase;

    // POST /waterside/shipping-orders - Create SO  
    @PostMapping
    @PreAuthorize("hasRole('SHIP_CAPTAIN')")
    public ResponseEntity<ShippingOrderResponseDto> submitShippingOrder(@RequestBody SubmitShippingOrderRequestDto requestDto) {
        log.info("Received shipping order submission request: {}", requestDto.getShippingOrderNumber());
        
        SubmitShippingOrderCommand command = new SubmitShippingOrderCommand(
            UUID.randomUUID(),
            requestDto.getShippingOrderNumber(),
            requestDto.getPurchaseOrderReference(),
            requestDto.getVesselNumber(),
            requestDto.getCustomerNumber(),
            requestDto.getEstimatedArrivalDate(),
            requestDto.getEstimatedDepartureDate(),
            requestDto.getActualArrivalDate()
        );
        
        ShippingOrder shippingOrder = submitShippingOrderUseCase.submitShippingOrder(command);
        ShippingOrderResponseDto responseDto = shippingOrderMapper.toShippingOrderResponseDto(shippingOrder);
        
        log.info("Shipping order submitted successfully: {}", shippingOrder.getShippingOrderId());
        
        return ResponseEntity.ok(responseDto);
    }

    // GET /waterside/shipping-orders - Get all shipping orders
    @GetMapping
    @PreAuthorize("hasAnyRole('SHIP_CAPTAIN', 'FOREMAN', 'INSPECTOR', 'BUNKERING_OFFICER')")
    public ResponseEntity<List<FullShippingOrderResponseDto>> getAllShippingOrders() {
        log.info("Fetching all shipping orders");
        List<ShippingOrder> shippingOrders = queryShippingOrderUseCase.getAllShippingOrders();
        List<FullShippingOrderResponseDto> responseDtos = shippingOrders.stream()
                .map(shippingOrderMapper::toFullShippingOrderResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
    
    // GET /waterside/shipping-orders/{id} - Get SO by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SHIP_CAPTAIN', 'FOREMAN', 'INSPECTOR', 'BUNKERING_OFFICER')")
    public ResponseEntity<FullShippingOrderResponseDto> getShippingOrderById(@PathVariable String id) {
        log.info("Fetching shipping order by ID: {}", id);
        try {
            UUID shippingOrderId = UUID.fromString(id);
            var shippingOrderOpt = queryShippingOrderUseCase.getShippingOrderById(shippingOrderId);
            
            if (shippingOrderOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            FullShippingOrderResponseDto responseDto = shippingOrderMapper.toFullShippingOrderResponseDto(shippingOrderOpt.get());
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format: {}", id);
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /waterside/shipping-orders/arrived - Get arrived ships
    @GetMapping("/arrived")
    @PreAuthorize("hasAnyRole('SHIP_CAPTAIN', 'FOREMAN', 'INSPECTOR', 'BUNKERING_OFFICER')")
    public ResponseEntity<List<FullShippingOrderResponseDto>> getArrivedShippingOrders() {
        log.info("Fetching arrived shipping orders");
        List<ShippingOrder> shippingOrders = queryShippingOrderUseCase.getArrivedShippingOrders();
        List<FullShippingOrderResponseDto> responseDtos = shippingOrders.stream()
                .map(shippingOrderMapper::toFullShippingOrderResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
    
    // GET /waterside/shipping-orders/loading - Get ships being loaded
    @GetMapping("/loading")
    @PreAuthorize("hasAnyRole('SHIP_CAPTAIN', 'FOREMAN', 'INSPECTOR', 'BUNKERING_OFFICER')")
    public ResponseEntity<List<FullShippingOrderResponseDto>> getLoadingShippingOrders() {
        log.info("Fetching loading shipping orders");
        List<ShippingOrder> shippingOrders = queryShippingOrderUseCase.getLoadingShippingOrders();
        List<FullShippingOrderResponseDto> responseDtos = shippingOrders.stream()
                .map(shippingOrderMapper::toFullShippingOrderResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    // GET /waterside/shipping-orders/departed - Get departed ships
    @GetMapping("/departed")
    @PreAuthorize("hasAnyRole('SHIP_CAPTAIN', 'FOREMAN', 'INSPECTOR', 'BUNKERING_OFFICER')")
    public ResponseEntity<List<FullShippingOrderResponseDto>> getDepartedShippingOrders() {
        log.info("Fetching departed shipping orders");
        List<ShippingOrder> shippingOrders = queryShippingOrderUseCase.getDepartedShippingOrders();
        List<FullShippingOrderResponseDto> responseDtos = shippingOrders.stream()
                .map(shippingOrderMapper::toFullShippingOrderResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
    
    // GET /waterside/shipping-orders/ready_for_loading - Get ready_for_loading ships
    @GetMapping("/ready_for_loading")
    @PreAuthorize("hasAnyRole('SHIP_CAPTAIN', 'FOREMAN', 'INSPECTOR', 'BUNKERING_OFFICER')")
    public ResponseEntity<List<FullShippingOrderResponseDto>> getReadyForLoadingShippingOrders() {
        log.info("Fetching ready for loading shipping orders");
        List<ShippingOrder> shippingOrders = queryShippingOrderUseCase.getReadyForLoadingShippingOrders();
        List<FullShippingOrderResponseDto> responseDtos = shippingOrders.stream()
                .map(shippingOrderMapper::toFullShippingOrderResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
    
    // GET /waterside/shipping-orders/by-vessel/{vesselNumber} - Get SO by vessel
    @GetMapping("/by-vessel/{vesselNumber}")
    @PreAuthorize("hasAnyRole('SHIP_CAPTAIN', 'FOREMAN', 'INSPECTOR', 'BUNKERING_OFFICER')")
    public ResponseEntity<List<FullShippingOrderResponseDto>> getShippingOrdersByVessel(@PathVariable String vesselNumber) {
        log.info("Fetching shipping orders by vessel: {}", vesselNumber);
        List<ShippingOrder> shippingOrders = queryShippingOrderUseCase.getShippingOrdersByVessel(vesselNumber);
        List<FullShippingOrderResponseDto> responseDtos = shippingOrders.stream()
                .map(shippingOrderMapper::toFullShippingOrderResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
    
    // GET /waterside/shipping-orders/by-status/{status} - Get SO by status
    @GetMapping("/by-status/{status}")
    @PreAuthorize("hasAnyRole('SHIP_CAPTAIN', 'FOREMAN', 'INSPECTOR', 'BUNKERING_OFFICER')")
    public ResponseEntity<List<FullShippingOrderResponseDto>> getShippingOrdersByStatus(@PathVariable String status) {
        log.info("Fetching shipping orders by status: {}", status);
        try {
            ShippingOrder.ShippingOrderStatus orderStatus = ShippingOrder.ShippingOrderStatus.valueOf(status.toUpperCase());
            List<ShippingOrder> shippingOrders = queryShippingOrderUseCase.getShippingOrdersByStatus(orderStatus);
            List<FullShippingOrderResponseDto> responseDtos = shippingOrders.stream()
                    .map(shippingOrderMapper::toFullShippingOrderResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDtos);
        } catch (IllegalArgumentException e) {
            log.error("Invalid status: {}", status);
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT /waterside/shipping-orders/{id} - Update SO
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SHIP_CAPTAIN')")
    public ResponseEntity<FullShippingOrderResponseDto> updateShippingOrder(
            @PathVariable String id, 
            @RequestBody UpdateShippingOrderRequestDto requestDto) {
        log.info("Updating shipping order: {}", id);
        
        try {
            UUID shippingOrderId = UUID.fromString(id);
            UpdateShippingOrderCommand command = new UpdateShippingOrderCommand();
            command.setShippingOrderNumber(requestDto.getShippingOrderNumber());
            command.setPurchaseOrderReference(requestDto.getPurchaseOrderReference());
            command.setVesselNumber(requestDto.getVesselNumber());
            command.setCustomerNumber(requestDto.getCustomerNumber());
            command.setEstimatedArrivalDate(requestDto.getEstimatedArrivalDate());
            command.setEstimatedDepartureDate(requestDto.getEstimatedDepartureDate());
            command.setActualArrivalDate(requestDto.getActualArrivalDate());
            command.setActualDepartureDate(requestDto.getActualDepartureDate());
            
            ShippingOrder updatedShippingOrder = updateShippingOrderUseCase.updateShippingOrder(shippingOrderId, command);
            FullShippingOrderResponseDto responseDto = shippingOrderMapper.toFullShippingOrderResponseDto(updatedShippingOrder);
            
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format or shipping order not found: {}", id);
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PATCH /waterside/shipping-orders/{id}/inspection - Complete inspection
    @PatchMapping("/{id}/inspection")
    @PreAuthorize("hasRole('INSPECTOR')")
    public ResponseEntity<FullShippingOrderResponseDto> completeInspection(
            @PathVariable String id, 
            @RequestBody CompleteInspectionRequestDto requestDto) {
        log.info("Completing inspection for shipping order: {}", id);
        
        try {
            UUID shippingOrderId = UUID.fromString(id);
            var command = new CompleteInspectionCommand(
                shippingOrderId, 
                requestDto.getInspectorSignature()
            );
            
            ShippingOrder completedShippingOrder = completeInspectionUseCase.completeInspection(command);
            FullShippingOrderResponseDto responseDto = shippingOrderMapper.toFullShippingOrderResponseDto(completedShippingOrder);
            
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format or shipping order not found: {}", id);
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PATCH /waterside/shipping-orders/{id}/bunkering - Complete bunkering
    @PatchMapping("/{id}/bunkering")
    @PreAuthorize("hasRole('BUNKERING_OFFICER')")
    public ResponseEntity<FullShippingOrderResponseDto> completeBunkering(
            @PathVariable String id, 
            @RequestBody CompleteBunkeringRequestDto requestDto) {
        log.info("Completing bunkering for shipping order: {}", id);
        
        try {
            UUID shippingOrderId = UUID.fromString(id);
            var command = new CompleteBunkeringCommand(
                shippingOrderId, 
                requestDto.getBunkeringOfficerSignature()
            );
            
            ShippingOrder completedShippingOrder = completeBunkeringUseCase.completeBunkering(command);
            FullShippingOrderResponseDto responseDto = shippingOrderMapper.toFullShippingOrderResponseDto(completedShippingOrder);
            
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format or shipping order not found: {}", id);
            return ResponseEntity.badRequest().build();
        }
    }
    
    // DELETE /waterside/shipping-orders/{id} - Delete SO
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SHIP_CAPTAIN')")
    public ResponseEntity<Void> deleteShippingOrder(@PathVariable String id) {
        log.info("Deleting shipping order: {}", id);
        
        try {
            UUID shippingOrderId = UUID.fromString(id);
            updateShippingOrderUseCase.deleteShippingOrder(shippingOrderId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format or shipping order not found: {}", id);
            return ResponseEntity.badRequest().build();
        }
    }
}