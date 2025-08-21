package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.GetTodaysAppointmentsUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetTodaysAppointmentsUseCaseImpl implements GetTodaysAppointmentsUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    public List<Appointment> getTodaysAppointments() {
        LocalDate today = LocalDate.now();
        log.info("Retrieving appointments for today: {}", today);
        
        List<Appointment> todaysAppointments = appointmentRepositoryPort.findByDate(today);
        
        log.info("Found {} appointments for today", todaysAppointments.size());
        return todaysAppointments;
    }
}
