package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.common.facades.CreateAppointmentCommand;
import be.kdg.prog6.landsideContext.domain.Appointment;

@FunctionalInterface
public interface CreateAppointmentUseCase {

    Appointment createAppointment(CreateAppointmentCommand command);
}
