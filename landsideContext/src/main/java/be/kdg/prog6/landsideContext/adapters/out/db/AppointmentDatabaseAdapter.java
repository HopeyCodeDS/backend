package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.ArrivalWindow;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentDatabaseAdapter implements AppointmentRepositoryPort {
    
    private final AppointmentJpaRepository appointmentJpaRepository;
    private final AppointmentMapper appointmentMapper;
    
    @Override
    @Transactional
    public void save(Appointment appointment) {
        try {
            log.info("Saving appointment for seller: {}", appointment.getSellerId());
            AppointmentJpaEntity jpaEntity = appointmentMapper.toJpaEntity(appointment);
            appointmentJpaRepository.save(jpaEntity);
            log.info("Successfully saved appointment with ID: {}", appointment.getAppointmentId());
        } catch (Exception e) {
            log.error("Error saving appointment: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Appointment> findById(UUID appointmentId) {
        return appointmentJpaRepository.findById(appointmentId)
                .map(appointmentMapper::toDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findByArrivalWindow(ArrivalWindow arrivalWindow) {
        try {
            log.info("Finding appointments for window: {} to {}", 
                    arrivalWindow.getStartTime(), arrivalWindow.getEndTime());
            List<AppointmentJpaEntity> jpaEntities = appointmentJpaRepository.findByArrivalWindowOverlap(
                arrivalWindow.getStartTime(), 
                arrivalWindow.getEndTime()
            );
            log.info("Found {} existing appointments", jpaEntities.size());
            return jpaEntities.stream()
                    .map(appointmentMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding appointments by arrival window: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findBySellerId(UUID sellerId) {
        List<AppointmentJpaEntity> jpaEntities = appointmentJpaRepository.findBySellerId(sellerId);
        return jpaEntities.stream()
                .map(appointmentMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findByLicensePlate(String licensePlate) {
        List<AppointmentJpaEntity> jpaEntities = appointmentJpaRepository.findByLicensePlate(licensePlate);
        return jpaEntities.stream()
                .map(appointmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findAll() {
        try {
            log.info("Finding all appointments");
            List<AppointmentJpaEntity> jpaEntities = appointmentJpaRepository.findAll();
            log.info("Found {} appointments", jpaEntities.size());
            return jpaEntities.stream()
                    .map(appointmentMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding all appointments: {}", e.getMessage(), e);
            throw e;
        }
    }
}