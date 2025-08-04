package be.kdg.prog6.watersideContext.ports.in;

import be.kdg.prog6.watersideContext.domain.ShipCaptainOperationsOverview;

public interface GetShipCaptainOperationsOverviewUseCase {
    ShipCaptainOperationsOverview getOperationsOverview(String vesselNumber);
} 