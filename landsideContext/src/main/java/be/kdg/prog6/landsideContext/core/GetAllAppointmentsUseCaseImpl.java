package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.GetAllAppointmentsUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllAppointmentsUseCaseImpl implements GetAllAppointmentsUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    
    @Override
    public List<Appointment> getAllAppointments() {
        log.info("Retrieving all appointments");
        return appointmentRepositoryPort.findAll();
    }
}
