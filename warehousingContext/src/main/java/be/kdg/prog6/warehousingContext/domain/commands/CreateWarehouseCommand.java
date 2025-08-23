package be.kdg.prog6.warehousingContext.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWarehouseCommand {
    private String warehouseNumber;
    private UUID sellerId;
    private String rawMaterialName;
    private double maxCapacity;
}
