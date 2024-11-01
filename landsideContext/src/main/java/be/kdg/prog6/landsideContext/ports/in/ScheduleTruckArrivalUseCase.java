package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.Truck;

import java.time.LocalDateTime;

@FunctionalInterface
public interface ScheduleTruckArrivalUseCase {
    void scheduleTruckArrival(Truck truck, LocalDateTime arrivalTime);
}
