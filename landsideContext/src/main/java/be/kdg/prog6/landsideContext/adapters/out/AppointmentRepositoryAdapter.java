package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.facade.AppointmentFacade;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class AppointmentRepositoryAdapter implements AppointmentRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentRepositoryAdapter.class);
    // Temporarily using map to store a list of appointments for each truck
    private final Map<String, List<Appointment>> appointments = new HashMap<>();

    @Override
    public void save(Appointment appointment) {
        // Retrieve the list of appointments for the truck or create a new one if none exist
        appointments.computeIfAbsent(appointment.getTruck().getLicensePlate(), k -> new ArrayList<>())
                .add(appointment); // I am adding the new appointment to the list

        System.out.println("Appointment saved for truck: " + appointment.getTruck().getLicensePlate());
    }

    @Override
    public Optional<Appointment> findBySellerId(UUID sellerId) {
        logger.info("Searching for appointment that matches the seller ID: {}", sellerId);
        // Find the first appointment that matches the seller ID
        return appointments.values().stream()
                .flatMap(List::stream) // Flatten the list of appointments
                .filter(appointment -> appointment.getSellerId().uuid().equals(sellerId))// Solved the failing equality check I had before
                .findFirst(); // Return the first matching appointment
    }

    @Override
    public List<Appointment> findAppointmentsDuringArrivalWindow(LocalDateTime start, LocalDateTime end) {
        logger.info("Searching for appointment that falls between {} and {}", start, end);
        // Filter appointments within the specified arrival window
        return appointments.values().stream()
                .flatMap(List::stream) // Flatten the list of appointments
                .filter(appointment -> {
                    LocalDateTime arrivalTime = appointment.getArrivalWindow();
                    return (arrivalTime.isEqual(start) || arrivalTime.isAfter(start)) &&
                            (arrivalTime.isEqual(end) || arrivalTime.isBefore(end));
                })
                .toList(); // Collect and return matching appointments
    }

    @Override
    public List<Appointment> findAppointmentsByTruckLicensePlate(String licensePlate) {
        return appointments.getOrDefault(licensePlate, Collections.emptyList());
    }

    @Override
    public Optional<Appointment> findByLicensePlate(String licensePlate) {
        return appointments.values().stream()
                .flatMap(List::stream)
                .filter(appointment -> appointment.getTruck().getLicensePlate().equals(licensePlate))
                .findFirst();
    }
}
