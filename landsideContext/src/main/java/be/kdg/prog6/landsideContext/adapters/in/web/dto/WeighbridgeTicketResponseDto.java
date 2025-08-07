package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public record WeighbridgeTicketResponseDto(
    UUID ticketId,
    String licensePlate,
    double grossWeight,
    double tareWeight,
    double netWeight,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime weighingTime
) {} 