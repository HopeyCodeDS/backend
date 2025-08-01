package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.mapper.ArrivalComplianceMapper;
import be.kdg.prog6.landsideContext.ports.in.GetArrivalComplianceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/landside/arrival-compliance")
@RequiredArgsConstructor
@Slf4j
public class ArrivalComplianceController {
    
    private final GetArrivalComplianceUseCase getArrivalComplianceUseCase;
    private final ArrivalComplianceMapper arrivalComplianceMapper;
    
    @GetMapping
    public ResponseEntity<?> getArrivalCompliance() {
        try {
            log.info("Getting arrival compliance data for warehouse manager");
            
            var complianceData = getArrivalComplianceUseCase.getArrivalCompliance();
            var responseDto = arrivalComplianceMapper.toDtoList(complianceData);
            
            log.info("Successfully retrieved {} arrival compliance records", responseDto.size());
            
            return ResponseEntity.ok(responseDto);
            
        } catch (Exception e) {
            log.error("Error getting arrival compliance data: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Internal server error",
                "message", "An unexpected error occurred while retrieving arrival compliance data"
            ));
        }
    }
} 