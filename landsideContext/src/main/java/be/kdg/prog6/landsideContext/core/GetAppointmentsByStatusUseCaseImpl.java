package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentsByStatusUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAppointmentsByStatusUseCaseImpl implements GetAppointmentsByStatusUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        log.info("Retrieving appointments with status: {}", status);
        List<Appointment> appointments = appointmentRepositoryPort.findByStatus(status);
        log.info("Found {} appointments with status: {}", appointments.size(), status);
        return appointments;
    }
}
