package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GetAppointmentUseCase {
    Appointment getAppointment(UUID appointmentId);

    List<Appointment> getAppointmentsByDate(LocalDate date);
}
