package be.kdg.prog6.invoicingContext.adapters.in.web;

import be.kdg.prog6.invoicingContext.adapters.in.web.dto.SubmitPurchaseOrderRequestDto;
import be.kdg.prog6.invoicingContext.adapters.in.web.dto.PurchaseOrderResponseDto;
import be.kdg.prog6.invoicingContext.adapters.in.web.mapper.PurchaseOrderMapper;
import be.kdg.prog6.invoicingContext.ports.in.SubmitPurchaseOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoicing/purchase-orders")
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderController {

    private final SubmitPurchaseOrderUseCase submitPurchaseOrderUseCase;
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
} 
