package be.kdg.prog6.warehousingContext.domain.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public record DeliverPayloadCommand(
    String licensePlate,
    String rawMaterialName,
    String warehouseNumber,
    double payloadWeight,
    UUID sellerId,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime deliveryTime,
    String newWeighingBridgeNumber
) {} 