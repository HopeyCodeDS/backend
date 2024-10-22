package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.Appointment;

import java.util.Optional;

@FunctionalInterface
public interface RecognizeTruckUseCase {
    Optional<Appointment> recognizeTruckAndValidateArrival(String licensePlate);
}
