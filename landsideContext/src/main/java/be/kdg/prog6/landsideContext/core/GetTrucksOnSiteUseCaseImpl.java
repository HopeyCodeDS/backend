package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.ports.in.GetTrucksOnSiteUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetTrucksOnSiteUseCaseImpl implements GetTrucksOnSiteUseCase {
    
    private final TruckMovementRepositoryPort truckMovementRepositoryPort;
    
    @Override
    public long getTrucksOnSiteCount() {
        log.info("Found {} trucks on site", truckMovementRepositoryPort.countTrucksOnSite());
        return truckMovementRepositoryPort.countTrucksOnSite();
    }
} 