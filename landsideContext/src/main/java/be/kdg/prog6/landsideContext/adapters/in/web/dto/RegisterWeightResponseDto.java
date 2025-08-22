package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterWeightResponseDto {
    private String licensePlate;
    private String status; // "PROCESSING", "WAREHOUSE_ASSIGNED", "ERROR"
    private String message;
    private String warehouseNumber; // null until assigned
} 