package be.kdg.prog6.watersideContext.ports.in;

import be.kdg.prog6.watersideContext.domain.ShipCaptainOperationsOverview;
import java.util.List;

public interface GetShipCaptainOperationsOverviewUseCase {
    ShipCaptainOperationsOverview getVesselOperations(String vesselNumber);
    List<ShipCaptainOperationsOverview> getAllVesselsOperations();
} 