package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipDepartureRequestDto {
    private String vesselNumber;
    private LocalDateTime departureDate;
}
