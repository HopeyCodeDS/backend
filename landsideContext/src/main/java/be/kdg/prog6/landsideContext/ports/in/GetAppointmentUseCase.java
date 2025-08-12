package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.Appointment;
import java.util.UUID;

public interface GetAppointmentUseCase {
    Appointment getAppointment(UUID appointmentId);
}
