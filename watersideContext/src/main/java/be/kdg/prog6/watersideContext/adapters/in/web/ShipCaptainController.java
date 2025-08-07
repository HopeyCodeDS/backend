package be.kdg.prog6.watersideContext.adapters.in.web;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.ShipCaptainOperationsOverviewDto;
import be.kdg.prog6.watersideContext.adapters.in.web.mapper.ShipCaptainMapper;
import be.kdg.prog6.watersideContext.domain.ShipCaptainOperationsOverview;
import be.kdg.prog6.watersideContext.ports.in.GetShipCaptainOperationsOverviewUseCase;
import be.kdg.prog6.watersideContext.adapters.in.web.ShipDepartureRequestDto;
import be.kdg.prog6.watersideContext.ports.in.ShipDepartedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.List;



@RestController
@RequestMapping("/waterside/captain")
@RequiredArgsConstructor
@Slf4j
public class ShipCaptainController {
    
    private final GetShipCaptainOperationsOverviewUseCase getShipCaptainOperationsOverviewUseCase;
    private final ShipCaptainMapper shipCaptainMapper;
    private final ShipDepartedUseCase shipDepartedUseCase;
    
    @GetMapping("/operations/{vesselNumber}")
    public ResponseEntity<ShipCaptainOperationsOverviewDto> getOperationsOverview(@PathVariable String vesselNumber) {
        log.info("Ship captain requesting operations overview for vessel: {}", vesselNumber);
        
        ShipCaptainOperationsOverview overview = getShipCaptainOperationsOverviewUseCase.getVesselOperations(vesselNumber);
        ShipCaptainOperationsOverviewDto response = shipCaptainMapper.toDto(overview);
        
        log.info("Returning operations overview for vessel {}: can leave port = {}", 
            vesselNumber, overview.isCanLeavePort());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/operations-overview")
    public ResponseEntity<List<ShipCaptainOperationsOverviewDto>> getOperationsOverview() {
        log.info("Ship captain requesting operations overview for all vessels");

        var allVessels = getShipCaptainOperationsOverviewUseCase.getAllVesselsOperations();
        var overview = allVessels.stream().map(shipCaptainMapper::toDto).collect(Collectors.toList());

        log.info("Ship captain overview: {} vessels", overview.size());
        return ResponseEntity.ok(overview);
    }

    @PostMapping("/depart")
    public ResponseEntity<Void> markShipAsDeparted(@RequestBody ShipDepartureRequestDto request) {
        shipDepartedUseCase.markShipAsDeparted(request.getVesselNumber(), request.getDepartureDate());
        return ResponseEntity.ok().build();
    }

} 