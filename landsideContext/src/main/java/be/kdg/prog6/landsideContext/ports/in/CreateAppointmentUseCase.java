package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.facades.CreateAppointmentCommand;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.LicensePlate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface CreateAppointmentUseCase {
//    Appointment createAppointment(UUID sellerId, String licensePlate, MaterialType materialType, LocalDateTime arrivalWindow);

//    Appointment createAppointment(UUID sellerId, String licensePlate, String materialType, LocalDateTime arrivalWindow);
    Appointment createAppointment(CreateAppointmentCommand command);
}
