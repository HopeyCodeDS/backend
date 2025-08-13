package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.ports.in.GetTrucksOnSiteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/landside/trucks")
@RequiredArgsConstructor
public class TruckOnSiteController {
    
    private final GetTrucksOnSiteUseCase getTrucksOnSiteUseCase;
    
    @GetMapping("/on-site/count")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')") 
    public ResponseEntity<TruckOnSiteCountResponse> getTrucksOnSiteCount() {
        long count = getTrucksOnSiteUseCase.getTrucksOnSiteCount();
        return ResponseEntity.ok(new TruckOnSiteCountResponse(count));
    }
    
    public record TruckOnSiteCountResponse(long trucksOnSite) {}
} 