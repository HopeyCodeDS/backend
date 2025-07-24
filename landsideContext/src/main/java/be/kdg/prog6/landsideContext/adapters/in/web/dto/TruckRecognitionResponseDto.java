package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import lombok.Data;

@Data
public class TruckRecognitionResponseDto {
    private boolean recognized;
    private String message;
    private String appointmentId;
    private String sellerId;
    private String rawMaterialName;
    private String arrivalWindow;
}
