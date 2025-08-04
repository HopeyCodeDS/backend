package be.kdg.prog6.watersideContext.adapters.in.web;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.ShipCaptainOperationsOverviewDto;
import be.kdg.prog6.watersideContext.adapters.in.web.mapper.ShipCaptainMapper;
import be.kdg.prog6.watersideContext.domain.ShipCaptainOperationsOverview;
import be.kdg.prog6.watersideContext.ports.in.GetShipCaptainOperationsOverviewUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waterside/captain")
@RequiredArgsConstructor
@Slf4j
public class ShipCaptainController {
    
    private final GetShipCaptainOperationsOverviewUseCase getShipCaptainOperationsOverviewUseCase;
    private final ShipCaptainMapper shipCaptainMapper;
    
    @GetMapping("/operations/{vesselNumber}")
    public ResponseEntity<ShipCaptainOperationsOverviewDto> getOperationsOverview(@PathVariable String vesselNumber) {
        log.info("Ship captain requesting operations overview for vessel: {}", vesselNumber);
        
        ShipCaptainOperationsOverview overview = getShipCaptainOperationsOverviewUseCase.getOperationsOverview(vesselNumber);
        ShipCaptainOperationsOverviewDto response = shipCaptainMapper.toDto(overview);
        
        log.info("Returning operations overview for vessel {}: can leave port = {}", 
            vesselNumber, overview.isCanLeavePort());
        
        return ResponseEntity.ok(response);
    }
} 