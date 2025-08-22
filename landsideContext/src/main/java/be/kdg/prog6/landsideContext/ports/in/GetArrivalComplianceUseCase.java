package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.ArrivalComplianceData;
import java.util.List;

public interface GetArrivalComplianceUseCase {
    List<ArrivalComplianceData> getArrivalCompliance();
} 