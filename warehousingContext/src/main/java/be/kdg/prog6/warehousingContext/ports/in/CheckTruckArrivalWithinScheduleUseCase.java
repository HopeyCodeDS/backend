package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.common.commands.CheckTruckArrivalCommand;
import be.kdg.prog6.warehousingContext.domain.TruckArrivalStatus;

public interface CheckTruckArrivalWithinScheduleUseCase {
    TruckArrivalStatus checkArrivalWithinSchedule(CheckTruckArrivalCommand command);
}
