package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.commands.UpdateAppointmentCommand;
import be.kdg.prog6.landsideContext.domain.Appointment;

public interface UpdateAppointmentUseCase {
    Appointment updateAppointment(UpdateAppointmentCommand command);
}
