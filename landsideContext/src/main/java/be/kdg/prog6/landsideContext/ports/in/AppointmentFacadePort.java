package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.common.events.AppointmentCreatedEvent;
import be.kdg.prog6.landsideContext.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentFacadePort {
    AppointmentCreatedEvent createAppointment(CreateAppointmentCommand command);

    Optional<Appointment> getAppointmentBySellerId(UUID sellerId);

    Optional<Appointment> getAppointmentBySellerIdAndMaterialType(UUID sellerId, String materialType);

    List<Appointment> getAppointmentsDuringArrivalWindow(LocalDateTime start, LocalDateTime end);

    List<Appointment> getAppointmentsByTruckLicensePlate(String licensePlate);
}
