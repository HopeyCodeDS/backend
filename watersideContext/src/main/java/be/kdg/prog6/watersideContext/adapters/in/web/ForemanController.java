package be.kdg.prog6.watersideContext.adapters.in.web;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.MatchShippingOrderRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.UnmatchedShippingOrderDto;
import be.kdg.prog6.watersideContext.adapters.in.web.mapper.ShippingOrderMapper;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.MatchShippingOrderWithPurchaseOrderCommand;
import be.kdg.prog6.watersideContext.ports.in.GetUnmatchedShippingOrdersUseCase;
import be.kdg.prog6.watersideContext.ports.in.MatchShippingOrderWithPurchaseOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/waterside/foreman")
@RequiredArgsConstructor
@Slf4j
public class ForemanController {
    
    private final GetUnmatchedShippingOrdersUseCase getUnmatchedShippingOrdersUseCase;
    private final MatchShippingOrderWithPurchaseOrderUseCase matchShippingOrderWithPurchaseOrderUseCase;
    private final ShippingOrderMapper shippingOrderMapper;
    
    @GetMapping("/unmatched-shipping-orders")
    public ResponseEntity<List<UnmatchedShippingOrderDto>> getUnmatchedShippingOrders() {
        log.info("Foreman requesting unmatched shipping orders");
        
        List<ShippingOrder> unmatchedShippingOrders = getUnmatchedShippingOrdersUseCase.getUnmatchedShippingOrders();
        
        List<UnmatchedShippingOrderDto> response = unmatchedShippingOrders.stream()
                .map(shippingOrderMapper::toUnmatchedShippingOrderDto)
                .collect(Collectors.toList());
        
        log.info("Returning {} unmatched shipping orders", response.size());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/match-shipping-order")
    public ResponseEntity<UnmatchedShippingOrderDto> matchShippingOrderWithPurchaseOrder(@RequestBody MatchShippingOrderRequestDto requestDto) {
        log.info("Foreman matching shipping order with purchase order: {}", requestDto.getShippingOrderId());
        
        MatchShippingOrderWithPurchaseOrderCommand command = new MatchShippingOrderWithPurchaseOrderCommand(
            requestDto.getShippingOrderId(),
            requestDto.getForemanSignature()
        );
        
        ShippingOrder matchedShippingOrder = matchShippingOrderWithPurchaseOrderUseCase.matchShippingOrderWithPurchaseOrder(command);
        UnmatchedShippingOrderDto response = shippingOrderMapper.toUnmatchedShippingOrderDto(matchedShippingOrder);
        
        log.info("Shipping order successfully matched with purchase order: {}", requestDto.getShippingOrderId());
        
        return ResponseEntity.ok(response);
    }
} 