package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


public record ArrivalWindowResponseDto(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime startTime,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime endTime,

        List<AppointmentSummaryDto> appointments
) {}

