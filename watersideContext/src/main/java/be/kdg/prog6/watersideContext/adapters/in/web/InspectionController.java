package be.kdg.prog6.watersideContext.adapters.in.web;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.CompleteInspectionRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.OutstandingInspectionDto;
import be.kdg.prog6.watersideContext.adapters.in.web.mapper.ShippingOrderMapper;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.CompleteInspectionCommand;
import be.kdg.prog6.watersideContext.ports.in.CompleteInspectionUseCase;
import be.kdg.prog6.watersideContext.ports.in.GetOutstandingInspectionsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/waterside/inspections")
@RequiredArgsConstructor
@Slf4j
public class InspectionController {
    
    private final GetOutstandingInspectionsUseCase getOutstandingInspectionsUseCase;
    private final CompleteInspectionUseCase completeInspectionUseCase;
    private final ShippingOrderMapper shippingOrderMapper;
    
    @GetMapping("/outstanding")
    @PreAuthorize("hasRole('INSPECTOR')")
    public ResponseEntity<List<OutstandingInspectionDto>> getOutstandingInspections() {
        log.info("Inspector requesting outstanding inspections");
        
        List<ShippingOrder> outstandingInspections = getOutstandingInspectionsUseCase.getOutstandingInspections();
        
        List<OutstandingInspectionDto> response = outstandingInspections.stream()
                .map(shippingOrderMapper::toOutstandingInspectionDto)
                .collect(Collectors.toList());
        
        log.info("Returning {} outstanding inspections", response.size());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/complete")
    @PreAuthorize("hasRole('INSPECTOR')")   
    public ResponseEntity<OutstandingInspectionDto> completeInspection(@RequestBody CompleteInspectionRequestDto requestDto) {
        log.info("Inspector completing inspection for shipping order: {}", requestDto.getShippingOrderId());
        
        CompleteInspectionCommand command = new CompleteInspectionCommand(
            requestDto.getShippingOrderId(),
            requestDto.getInspectorSignature()
        );
        
        ShippingOrder completedShippingOrder = completeInspectionUseCase.completeInspection(command);
        OutstandingInspectionDto response = shippingOrderMapper.toOutstandingInspectionDto(completedShippingOrder);
        
        log.info("Inspection completed successfully for shipping order: {}", requestDto.getShippingOrderId());
        
        return ResponseEntity.ok(response);
    }
} 