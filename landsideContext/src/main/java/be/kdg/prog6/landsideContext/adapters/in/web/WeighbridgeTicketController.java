package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.GenerateWeighbridgeTicketRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.WeighbridgeTicketResponseDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.WeighbridgeTicketWebMapper;
import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import be.kdg.prog6.landsideContext.domain.commands.GenerateWeighbridgeTicketCommand;
import be.kdg.prog6.landsideContext.ports.in.GenerateWeighbridgeTicketUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/landside/weighbridge-ticket")
@RequiredArgsConstructor
public class WeighbridgeTicketController {
    
    private final GenerateWeighbridgeTicketUseCase generateWeighbridgeTicketUseCase;
    private final WeighbridgeTicketWebMapper weighbridgeTicketMapper;
    
    @PostMapping("/generate")
    @PreAuthorize("hasRole('TRUCK_DRIVER')") 
    public ResponseEntity<WeighbridgeTicketResponseDto> generateWeighbridgeTicket(
            @RequestBody GenerateWeighbridgeTicketRequestDto requestDto) {
        
        GenerateWeighbridgeTicketCommand command = weighbridgeTicketMapper.toCommand(requestDto);
        WeighbridgeTicket ticket = generateWeighbridgeTicketUseCase.generateWeighbridgeTicket(command);
        
        WeighbridgeTicketResponseDto responseDto = weighbridgeTicketMapper.toResponseDto(ticket);
        
        return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping("/{licensePlate}")
    @PreAuthorize("hasRole('TRUCK_DRIVER')") 
    public ResponseEntity<List<WeighbridgeTicketResponseDto>> getTicketsByLicensePlate(
            @PathVariable String licensePlate) {
        
        // This would require adding a query use case
        // For now, return success with empty list
        return ResponseEntity.ok(List.of());
    }
} 