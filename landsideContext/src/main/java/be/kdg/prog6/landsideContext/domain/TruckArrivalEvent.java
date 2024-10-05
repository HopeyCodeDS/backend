package be.kdg.prog6.landsideContext.domain;

import java.time.LocalDateTime;

public record TruckArrivalEvent(LicensePlate licensePlate, LocalDateTime arrivalTime) {
}
