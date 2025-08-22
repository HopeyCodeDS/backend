package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import lombok.Data;

@Data
public class RegisterWeightRequestDto {
    private String licensePlate;
    
    private Double weight;
    
    private String rawMaterialName;
} 