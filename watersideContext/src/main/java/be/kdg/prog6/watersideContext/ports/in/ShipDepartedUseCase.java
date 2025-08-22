package be.kdg.prog6.watersideContext.ports.in;

import java.time.LocalDateTime;

public interface ShipDepartedUseCase {
    void markShipAsDeparted(String vesselNumber, LocalDateTime departureDate);
} 