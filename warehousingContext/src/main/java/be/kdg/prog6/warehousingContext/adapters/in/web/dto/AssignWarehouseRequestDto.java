package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AssignWarehouseRequestDto {
    private String licensePlate;
    private String rawMaterialName;
    private UUID sellerId;
    private double truckWeight;
}