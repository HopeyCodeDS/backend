package be.kdg.prog6.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShipDepartedEvent(
    UUID shippingOrderId,
    String purchaseOrderReference,
    String vesselNumber,
    LocalDateTime departureDate
) {} 