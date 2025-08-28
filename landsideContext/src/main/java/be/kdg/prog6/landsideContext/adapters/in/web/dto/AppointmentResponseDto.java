package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.ArrivalWindow;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDto(
    UUID appointmentId,
    UUID sellerId,
    String sellerName,
    String licensePlate,
    String truckType,
    String rawMaterialName,
    AppointmentStatus status,
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime scheduledTime,
    ArrivalWindowResponseDto arrivalWindow
) {}
