package be.kdg.prog6.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record WarehouseActivityEvent(
    UUID activityId,
    String customerNumber, // This is the sellerId
    String warehouseNumber,
    String action, // "LOADING_VESSEL", "PAYLOAD_DELIVERED"
    double amount,
    String materialType,
    LocalDateTime deliveryTime,
    String vesselNumber  // It can be also be the truck licensePlate
) {} 