package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentByLicensePlateUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAppointmentByLicensePlateUseCaseImpl implements GetAppointmentByLicensePlateUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    public Optional<Appointment> getAppointmentByLicensePlate(String licensePlate) {
        return appointmentRepositoryPort.findByLicensePlate(licensePlate)
                .stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.ARRIVED)
                .findFirst();
    }
}