package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShipLoadedEvent(
    UUID shippingOrderId,
    String purchaseOrderReference,
    String vesselNumber,
    String customerNumber,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime loadingCompletionDate
) {}