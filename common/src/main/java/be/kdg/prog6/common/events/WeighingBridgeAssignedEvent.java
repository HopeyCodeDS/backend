package be.kdg.prog6.common.events;

import java.time.LocalDateTime;

public record WeighingBridgeAssignedEvent(String licensePlate, String weighingBridgeNumber, LocalDateTime timestamp) {
}
