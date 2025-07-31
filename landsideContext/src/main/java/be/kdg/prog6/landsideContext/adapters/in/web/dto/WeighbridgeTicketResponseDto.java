package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import java.util.UUID;

public record WeighbridgeTicketResponseDto(
    UUID ticketId,
    String licensePlate,
    double grossWeight,
    double tareWeight,
    double netWeight,
    String weighingTime
) {} 