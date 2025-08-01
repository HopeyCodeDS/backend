package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.ports.in.GetTrucksOnSiteUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTrucksOnSiteUseCaseImpl implements GetTrucksOnSiteUseCase {
    
    private final TruckMovementRepositoryPort truckMovementRepositoryPort;
    
    @Override
    public long getTrucksOnSiteCount() {
        return truckMovementRepositoryPort.countTrucksOnSite();
    }
} 