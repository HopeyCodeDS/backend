package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.ArrivalWindow;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepositoryPort {
    void save(Appointment appointment);
    void deleteById(UUID appointmentId);
    Optional<Appointment> findById(UUID appointmentId);
    List<Appointment> findByArrivalWindow(ArrivalWindow arrivalWindow);
    List<Appointment> findBySellerId(UUID sellerId);
    List<Appointment> findByLicensePlate(String licensePlate);
    List<Appointment> findAll();
    List<Appointment> findByStatus(AppointmentStatus status);
} 