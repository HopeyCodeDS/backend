package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import java.util.List;

public interface GetAppointmentsByStatusUseCase {
    List<Appointment> getAppointmentsByStatus(AppointmentStatus status);
}
