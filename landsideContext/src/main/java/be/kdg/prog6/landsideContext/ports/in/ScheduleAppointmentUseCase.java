package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import java.util.UUID;

public interface ScheduleAppointmentUseCase {
    UUID scheduleAppointment(ScheduleAppointmentCommand command);
} 