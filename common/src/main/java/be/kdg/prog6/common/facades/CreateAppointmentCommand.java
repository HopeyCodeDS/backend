package be.kdg.prog6.common.facades;

import be.kdg.prog6.common.domain.MaterialType;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateAppointmentCommand(UUID sellerId, String plateNumber, MaterialType materialType, LocalDateTime arrivalWindow) {
}
