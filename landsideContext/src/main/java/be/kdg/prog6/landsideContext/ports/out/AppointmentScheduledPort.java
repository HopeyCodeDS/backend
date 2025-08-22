package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.Appointment;

public interface AppointmentScheduledPort {
    void appointmentScheduled(Appointment appointment);
} 