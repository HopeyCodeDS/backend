package be.kdg.prog6.invoicingContext.adapters.in.web;

import be.kdg.prog6.invoicingContext.adapters.in.web.dto.SubmitPurchaseOrderRequestDto;
import be.kdg.prog6.invoicingContext.adapters.in.web.dto.UpdatePurchaseOrderRequestDto;
import be.kdg.prog6.invoicingContext.adapters.in.web.dto.UpdatePurchaseOrderStatusRequestDto;
import be.kdg.prog6.invoicingContext.adapters.in.web.dto.PurchaseOrderResponseDto;
import be.kdg.prog6.invoicingContext.adapters.in.web.mapper.PurchaseOrderMapper;
import be.kdg.prog6.invoicingContext.domain.commands.UpdatePurchaseOrderCommand;
import be.kdg.prog6.invoicingContext.domain.commands.UpdatePurchaseOrderStatusCommand;
import be.kdg.prog6.invoicingContext.ports.in.DeletePurchaseOrderUseCase;
import be.kdg.prog6.invoicingContext.ports.in.QueryPurchaseOrderUseCase;
import be.kdg.prog6.invoicingContext.ports.in.UpdatePurchaseOrderUseCase;
import be.kdg.prog6.invoicingContext.ports.in.SubmitPurchaseOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoicing/purchase-orders")
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderController {

    private final SubmitPurchaseOrderUseCase submitPurchaseOrderUseCase;
    private final QueryPurchaseOrderUseCase queryPurchaseOrderUseCase;
    private final UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase;
    private final DeletePurchaseOrderUseCase deletePurchaseOrderUseCase;
    private final PurchaseOrderMapper purchaseOrderMapper;

    @PostMapping
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<PurchaseOrderResponseDto> submitPurchaseOrder(@RequestBody SubmitPurchaseOrderRequestDto requestDto) {
        log.info("Received purchase order submission request: {}", requestDto.getPurchaseOrderNumber());
        
        try {
            var command = purchaseOrderMapper.toCommand(requestDto);
            var purchaseOrder = submitPurchaseOrderUseCase.submitPurchaseOrder(command);
            var responseDto = purchaseOrderMapper.toResponseDto(purchaseOrder);
            
            log.info("Purchase order submitted successfully: {}", purchaseOrder.getPurchaseOrderId());
            
            return ResponseEntity.ok(responseDto);
            
        } catch (IllegalArgumentException e) {
            log.error("Invalid purchase order data: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error submitting purchase order: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /invoicing/purchase-orders - Get all purchase orders
    @GetMapping
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER') or hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<PurchaseOrderResponseDto>> getAllPurchaseOrders() {
        log.info("Retrieving all purchase orders");
        
        try {
            var purchaseOrders = queryPurchaseOrderUseCase.getAllPurchaseOrders();
            var responseDtos = purchaseOrders.stream()
                    .map(purchaseOrderMapper::toResponseDto)
                    .collect(Collectors.toList());
            
            log.info("Retrieved {} purchase orders successfully", responseDtos.size());
            return ResponseEntity.ok(responseDtos);
            
        } catch (Exception e) {
            log.error("Error retrieving all purchase orders: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /invoicing/purchase-orders/{id} - Get PO by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER') or hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<PurchaseOrderResponseDto> getPurchaseOrderById(@PathVariable UUID id) {
        log.info("Retrieving purchase order by ID: {}", id);
        
        try {
            var purchaseOrderOpt = queryPurchaseOrderUseCase.getPurchaseOrderById(id);
            
            if (purchaseOrderOpt.isEmpty()) {
                log.warn("Purchase order not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            var responseDto = purchaseOrderMapper.toResponseDto(purchaseOrderOpt.get());
            log.info("Retrieved purchase order successfully: {}", id);
            return ResponseEntity.ok(responseDto);
            
        } catch (Exception e) {
            log.error("Error retrieving purchase order by ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /invoicing/purchase-orders/outstanding - Get outstanding POs
    @GetMapping("/outstanding")
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER') or hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<PurchaseOrderResponseDto>> getOutstandingPurchaseOrders() {
        log.info("Retrieving outstanding purchase orders");
        
        try {
            var purchaseOrders = queryPurchaseOrderUseCase.getOutstandingPurchaseOrders();
            var responseDtos = purchaseOrders.stream()
                    .map(purchaseOrderMapper::toResponseDto)
                    .collect(Collectors.toList());
            
            log.info("Retrieved {} outstanding purchase orders successfully", responseDtos.size());
            return ResponseEntity.ok(responseDtos);
            
        } catch (Exception e) {
            log.error("Error retrieving outstanding purchase orders: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /invoicing/purchase-orders/fulfilled - Get fulfilled POs
    @GetMapping("/fulfilled")
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER') or hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<PurchaseOrderResponseDto>> getFulfilledPurchaseOrders() {
        log.info("Retrieving fulfilled purchase orders");
        
        try {
            var purchaseOrders = queryPurchaseOrderUseCase.getFulfilledPurchaseOrders();
            var responseDtos = purchaseOrders.stream()
                    .map(purchaseOrderMapper::toResponseDto)
                    .collect(Collectors.toList());
            
            log.info("Retrieved {} fulfilled purchase orders successfully", responseDtos.size());
            return ResponseEntity.ok(responseDtos);
            
        } catch (Exception e) {
            log.error("Error retrieving fulfilled purchase orders: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /invoicing/purchase-orders/cancelled - Get cancelled POs
    @GetMapping("/cancelled")
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER') or hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<PurchaseOrderResponseDto>> getCancelledPurchaseOrders() {
        log.info("Retrieving cancelled purchase orders");
        
        try {
            var purchaseOrders = queryPurchaseOrderUseCase.getCancelledPurchaseOrders();
            var responseDtos = purchaseOrders.stream()
                    .map(purchaseOrderMapper::toResponseDto)
                    .collect(Collectors.toList());
            
            log.info("Retrieved {} cancelled purchase orders successfully", responseDtos.size());
            return ResponseEntity.ok(responseDtos);
            
        } catch (Exception e) {
            log.error("Error retrieving cancelled purchase orders: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /invoicing/purchase-orders/by-customer/{customerId} - Get POs by customer
    @GetMapping("/by-customer/{customerId}")
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER') or hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<PurchaseOrderResponseDto>> getPurchaseOrdersByCustomer(@PathVariable String customerId) {
        log.info("Retrieving purchase orders for customer: {}", customerId);
        
        try {
            var purchaseOrders = queryPurchaseOrderUseCase.getPurchaseOrdersByCustomer(customerId);
            var responseDtos = purchaseOrders.stream()
                    .map(purchaseOrderMapper::toResponseDto)
                    .collect(Collectors.toList());
            
            log.info("Retrieved {} purchase orders for customer {} successfully", responseDtos.size(), customerId);
            return ResponseEntity.ok(responseDtos);
            
        } catch (Exception e) {
            log.error("Error retrieving purchase orders for customer {}: {}", customerId, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /invoicing/purchase-orders/by-status/{status} - Get POs by status
    @GetMapping("/by-status/{status}")
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER') or hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<PurchaseOrderResponseDto>> getPurchaseOrdersByStatus(@PathVariable String status) {
        log.info("Retrieving purchase orders by status: {}", status);
        
        try {
            var purchaseOrders = queryPurchaseOrderUseCase.getPurchaseOrdersByStatus(status);
            var responseDtos = purchaseOrders.stream()
                    .map(purchaseOrderMapper::toResponseDto)
                    .collect(Collectors.toList());
            
            log.info("Retrieved {} purchase orders with status {} successfully", responseDtos.size(), status);
            return ResponseEntity.ok(responseDtos);
            
        } catch (Exception e) {
            log.error("Error retrieving purchase orders by status {}: {}", status, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // PUT /invoicing/purchase-orders/{id} - Update PO
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER')")
    public ResponseEntity<PurchaseOrderResponseDto> updatePurchaseOrder(
            @PathVariable UUID id, 
            @RequestBody UpdatePurchaseOrderRequestDto requestDto) {
        log.info("Updating purchase order: {}", id);
        
        try {
            // Convert to command
            var command = new UpdatePurchaseOrderCommand(
                    id,
                    requestDto.getPurchaseOrderNumber(),
                    requestDto.getCustomerNumber(),
                    requestDto.getCustomerName(),
                    requestDto.getSellerId(),
                    requestDto.getSellerName(),
                    requestDto.getOrderDate(),
                    requestDto.getOrderLines().stream()
                            .map(line -> new UpdatePurchaseOrderCommand.PurchaseOrderLineCommand(
                                    line.getLineNumber(),
                                    line.getRawMaterialName(),
                                    line.getAmountInTons(),
                                    line.getPricePerTon()))
                            .collect(Collectors.toList())
            );
            
            var updatedPurchaseOrder = updatePurchaseOrderUseCase.updatePurchaseOrder(command);
            var responseDto = purchaseOrderMapper.toResponseDto(updatedPurchaseOrder);
            
            log.info("Purchase order updated successfully: {}", id);
            return ResponseEntity.ok(responseDto);
            
        } catch (IllegalArgumentException e) {
            log.error("Invalid purchase order data for update: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error updating purchase order {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // PATCH /invoicing/purchase-orders/{id}/status - Update PO status
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('BUYER') or hasRole('SELLER') or hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<PurchaseOrderResponseDto> updatePurchaseOrderStatus(
            @PathVariable UUID id, 
            @RequestBody UpdatePurchaseOrderStatusRequestDto requestDto) {
        log.info("Updating status for purchase order: {} to {}", id, requestDto.getStatus());
        
        try {
            var command = new UpdatePurchaseOrderStatusCommand(id, requestDto.getStatus());
            var updatedPurchaseOrder = updatePurchaseOrderUseCase.updatePurchaseOrderStatus(command);
            var responseDto = purchaseOrderMapper.toResponseDto(updatedPurchaseOrder);
            
            log.info("Purchase order status updated successfully: {} to {}", id, requestDto.getStatus());
            return ResponseEntity.ok(responseDto);
            
        } catch (IllegalArgumentException e) {
            log.error("Invalid status update request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error updating purchase order status for {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE /invoicing/purchase-orders/{id} - Delete PO
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable UUID id) {
        log.info("Deleting purchase order: {}", id);
        
        try {
            deletePurchaseOrderUseCase.deletePurchaseOrder(id);
            
            log.info("Purchase order deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            log.error("Purchase order not found for deletion: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting purchase order {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
