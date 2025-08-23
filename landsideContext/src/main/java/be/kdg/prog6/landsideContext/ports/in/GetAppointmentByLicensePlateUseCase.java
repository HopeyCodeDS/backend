package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.Appointment;

import java.util.Optional;

public interface GetAppointmentByLicensePlateUseCase {

    Optional<Appointment> getAppointmentByLicensePlate(String licensePlate);
}