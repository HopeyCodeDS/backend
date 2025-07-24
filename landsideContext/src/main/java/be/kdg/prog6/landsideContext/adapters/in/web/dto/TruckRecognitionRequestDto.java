package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TruckRecognitionRequestDto {
    private String licensePlate;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime recognitionTime;
}
