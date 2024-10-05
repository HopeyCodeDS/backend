package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetAppointmentUseCaseImpl implements GetAppointmentUseCase {
    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public GetAppointmentUseCaseImpl(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }


    @Override
    public Optional<Appointment> getAppointmentBySellerId(UUID sellerId) {
        return appointmentRepositoryPort.findBySellerId(sellerId);
    }

    @Override
    public Optional<Appointment> getAppointmentBySellerIdAndMaterialType(UUID sellerId, String materialType) {
        // Fetch appointments and filter by sellerId and materialType
        return appointmentRepositoryPort.findBySellerId(sellerId).filter(appointment ->
                appointment.getMaterialType().name().equals(materialType)
        );
    }

    @Override
    public List<Appointment> getAppointmentsDuringArrivalWindow(LocalDateTime start, LocalDateTime end) {
        return appointmentRepositoryPort.findAppointmentsDuringArrivalWindow(start, end);
    }

    @Override
    public List<Appointment> getAppointmentsByTruckLicensePlate(String truckLicensePlate) {
        return appointmentRepositoryPort.findAppointmentsByTruckLicensePlate(truckLicensePlate);
    }
}
