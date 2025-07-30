package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import lombok.Data;

@Data
public class AssignWarehouseRequestDto {
    private String licensePlate;
    private String rawMaterialName;
    private String sellerId;
    private double truckWeight;
}