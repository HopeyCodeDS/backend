package be.kdg.prog6.warehousingContext.domain.commands;

import java.time.LocalDateTime;

public record DeliverPayloadCommand(
    String licensePlate,
    String rawMaterialName,
    String warehouseNumber,
    double payloadWeight,
    String sellerId,
    LocalDateTime deliveryTime,
    String newWeighingBridgeNumber
) {} 