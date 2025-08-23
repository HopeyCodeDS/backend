package be.kdg.prog6.landsideContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record EnrichedTruckData(
    UUID truckId,
    String licensePlate,
    String material,
    LocalDateTime plannedArrival,
    LocalDateTime actualArrival,
    String status,
    UUID sellerId,
    String sellerName,
    String warehouseNumber,
    Double grossWeight,
    Double tareWeight,
    Double netWeight
) {}
