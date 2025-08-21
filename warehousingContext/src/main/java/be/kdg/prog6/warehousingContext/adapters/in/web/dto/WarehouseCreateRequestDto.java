package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseCreateRequestDto {
    private String warehouseNumber;
    private UUID sellerId;
    private String rawMaterialName;
    private double maxCapacity;
}
