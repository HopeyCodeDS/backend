package be.kdg.prog6.watersideContext.adapters.in.web;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.ShippingOrderResponseDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.SubmitShippingOrderRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.mapper.ShippingOrderMapper;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.SubmitShippingOrderCommand;
import be.kdg.prog6.watersideContext.ports.in.SubmitShippingOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/waterside/shipping-orders")
@RequiredArgsConstructor
@Slf4j
public class ShippingOrderController {
    
    private final SubmitShippingOrderUseCase submitShippingOrderUseCase;
    private final ShippingOrderMapper shippingOrderMapper;
    
    @PostMapping
    public ResponseEntity<ShippingOrderResponseDto> submitShippingOrder(@RequestBody SubmitShippingOrderRequestDto requestDto) {
        log.info("Received shipping order submission request: {}", requestDto.getShippingOrderNumber());
        
        SubmitShippingOrderCommand command = new SubmitShippingOrderCommand(
            UUID.randomUUID(),
            requestDto.getShippingOrderNumber(),
            requestDto.getPurchaseOrderReference(),
            requestDto.getVesselNumber(),
            requestDto.getCustomerNumber(),
            requestDto.getEstimatedArrivalDate(),
            requestDto.getEstimatedDepartureDate()
        );
        
        ShippingOrder shippingOrder = submitShippingOrderUseCase.submitShippingOrder(command);
        ShippingOrderResponseDto responseDto = shippingOrderMapper.toShippingOrderResponseDto(shippingOrder);
        
        log.info("Shipping order submitted successfully: {}", shippingOrder.getShippingOrderId());
        
        return ResponseEntity.ok(responseDto);
    }
}