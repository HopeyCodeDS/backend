package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.ArrivalComplianceData;
import be.kdg.prog6.landsideContext.ports.in.GetArrivalComplianceUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetArrivalComplianceUseCaseImpl implements GetArrivalComplianceUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    
    @Override
    public List<ArrivalComplianceData> getArrivalCompliance() {
        log.info("Getting arrival compliance data for warehouse manager");
        
        // Get all appointments
        List<Appointment> appointments = appointmentRepositoryPort.findAll();
        
        log.info("Found {} appointments", appointments.size());
        
        // Convert to compliance data
        List<ArrivalComplianceData> complianceData = appointments.stream()
            .map(ArrivalComplianceData::new)
            .collect(Collectors.toList());
        
        log.info("Generated compliance data for {} appointments", complianceData.size());
        
        return complianceData;
    }
} 