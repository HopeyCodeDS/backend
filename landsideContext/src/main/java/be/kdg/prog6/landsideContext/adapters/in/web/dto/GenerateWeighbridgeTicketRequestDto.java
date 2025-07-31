package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record GenerateWeighbridgeTicketRequestDto(
    String licensePlate,
    double grossWeight,
    double tareWeight,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime weighingTime
) {} 