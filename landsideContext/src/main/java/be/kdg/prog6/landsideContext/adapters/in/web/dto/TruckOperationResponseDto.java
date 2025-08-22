package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public record TruckOperationResponseDto(
    UUID id,
    String licensePlate,
    String material,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime plannedArrival,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime actualArrival,
    String status,
    UUID sellerId,
    String sellerName,
    String warehouseNumber,
    Double grossWeight,
    Double tareWeight,
    Double netWeight
) {}
