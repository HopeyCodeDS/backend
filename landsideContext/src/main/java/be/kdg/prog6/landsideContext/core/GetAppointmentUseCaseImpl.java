package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAppointmentUseCaseImpl implements GetAppointmentUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    public Appointment getAppointment(UUID appointmentId) {
        log.info("Retrieving appointment with ID: {}", appointmentId);
        return appointmentRepositoryPort.findById(appointmentId).orElse(null);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        log.info("Retrieving appointments for date: {}", date);
        List<Appointment> appointments = appointmentRepositoryPort.findByDate(date);
        log.info("Found {} appointments for date: {}", appointments.size(), date);
        return appointments;
    }
}
