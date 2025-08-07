package be.kdg.prog6.warehousingContext.domain.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record DeliverPayloadCommand(
    String licensePlate,
    String rawMaterialName,
    String warehouseNumber,
    double payloadWeight,
    String sellerId,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime deliveryTime,
    String newWeighingBridgeNumber
) {} 