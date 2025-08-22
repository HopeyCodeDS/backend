package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.Appointment;
import java.util.List;

public interface GetTodaysAppointmentsUseCase {
    List<Appointment> getTodaysAppointments();
}