package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepositoryPort {

    void save(Appointment appointment);
    Optional<Appointment> findBySellerId(UUID id);
    List<Appointment> findAppointmentsDuringArrivalWindow(LocalDateTime start, LocalDateTime end);
    List<Appointment> findAppointmentsByTruckLicensePlate(String licensePlate);
    Optional<Appointment> findByLicensePlate(String licensePlate);

    Appointment saveAppointment(Appointment appointment);
}
