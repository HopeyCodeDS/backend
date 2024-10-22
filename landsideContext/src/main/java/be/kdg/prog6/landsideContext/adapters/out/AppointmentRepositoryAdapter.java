package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.adapters.out.db.mappers.AppointmentMapper;
import be.kdg.prog6.landsideContext.adapters.out.db.repositories.AppointmentJpaRepository;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepositoryAdapter implements AppointmentRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentRepositoryAdapter.class);

    private final AppointmentJpaRepository appointmentJpaRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentRepositoryAdapter(AppointmentJpaRepository appointmentJpaRepository, AppointmentMapper appointmentMapper) {
        this.appointmentJpaRepository = appointmentJpaRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public void save(Appointment appointment) {
        logger.info("Saving appointment for truck: {}", appointment.getTruck().getLicensePlate());
        // Convert the domain object to JPA entity and save it using the mapper
        appointmentJpaRepository.save(appointmentMapper.domainToEntity(appointment));
    }

    @Override
    public Optional<Appointment> findBySellerId(UUID sellerId) {
        logger.info("Searching for appointment with seller ID: {}", sellerId);
        // Use JPA to find by sellerId, then map to domain object
        return appointmentJpaRepository.findBySellerId(sellerId)
                .map(appointmentMapper::entityToDomain);
    }

    @Override
    public List<Appointment> findAppointmentsDuringArrivalWindow(LocalDateTime start, LocalDateTime end) {
        logger.info("Searching for appointments between {} and {}", start, end);
        // Use JPA to find appointments within the specified window and map them to domain objects
        return appointmentJpaRepository.findByArrivalWindowBetween(start, end)
                .stream()
                .map(appointmentMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findAppointmentsByTruckLicensePlate(String licensePlate) {
        logger.info("Searching for appointments by truck license plate: {}", licensePlate);
        // Use JPA to find appointments by truck license plate and map them to domain objects
        return appointmentJpaRepository.findByLicensePlate(licensePlate)
                .stream()
                .map(appointmentMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Appointment> findByLicensePlate(String licensePlate) {
        logger.info("Searching for appointment by truck license plate: {}", licensePlate);
        // Use JPA to find by license plate, then map to domain object
        return appointmentJpaRepository.findByLicensePlate(licensePlate)
                .map(appointmentMapper::entityToDomain);
    }
}
