package be.kdg.prog6.watersideContext.adapters.in.web;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.CompleteBunkeringRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.OutstandingBunkeringDto;
import be.kdg.prog6.watersideContext.adapters.in.web.mapper.ShippingOrderMapper;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.CompleteBunkeringCommand;
import be.kdg.prog6.watersideContext.ports.in.CompleteBunkeringUseCase;
import be.kdg.prog6.watersideContext.ports.in.GetOutstandingBunkeringUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/waterside/bunkering")
@RequiredArgsConstructor
@Slf4j
public class BunkeringController {
    
    private final GetOutstandingBunkeringUseCase getOutstandingBunkeringUseCase;
    private final CompleteBunkeringUseCase completeBunkeringUseCase;
    private final ShippingOrderMapper shippingOrderMapper;
    
    @GetMapping("/outstanding")
    @PreAuthorize("hasRole('BUNKERING_OFFICER')") 
    public ResponseEntity<List<OutstandingBunkeringDto>> getOutstandingBunkering() {
        log.info("Bunkering officer requesting outstanding bunkering operations");
        
        List<ShippingOrder> outstandingBunkering = getOutstandingBunkeringUseCase.getOutstandingBunkering();
        
        List<OutstandingBunkeringDto> response = outstandingBunkering.stream()
                .map(shippingOrderMapper::toOutstandingBunkeringDto)
                .collect(Collectors.toList());
        
        log.info("Returning {} outstanding bunkering operations", response.size());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/complete")
    @PreAuthorize("hasRole('BUNKERING_OFFICER')") 
    public ResponseEntity<OutstandingBunkeringDto> completeBunkering(@RequestBody CompleteBunkeringRequestDto requestDto) {
        log.info("Bunkering officer completing bunkering for shipping order: {}", requestDto.getShippingOrderId());
        
        CompleteBunkeringCommand command = new CompleteBunkeringCommand(
            requestDto.getShippingOrderId(),
            requestDto.getBunkeringOfficerSignature()
        );
        
        ShippingOrder completedShippingOrder = completeBunkeringUseCase.completeBunkering(command);
        OutstandingBunkeringDto response = shippingOrderMapper.toOutstandingBunkeringDto(completedShippingOrder);
        
        log.info("Bunkering completed successfully for shipping order: {}, now ready for loading {}", requestDto.getShippingOrderId(), completedShippingOrder.getStatus());
        
        return ResponseEntity.ok(response);
    }
} 