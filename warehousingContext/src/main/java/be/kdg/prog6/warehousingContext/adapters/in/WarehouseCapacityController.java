package be.kdg.prog6.warehousingContext.adapters.in;

import be.kdg.prog6.warehousingContext.core.WarehouseCapacityProjection;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehouseCapacityController {

    private final WarehouseCapacityProjection warehouseCapacityProjection;

    public WarehouseCapacityController(WarehouseCapacityProjection warehouseCapacityProjection) {
        this.warehouseCapacityProjection = warehouseCapacityProjection;
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getWarehousesCapacityInfo() {
        return ResponseEntity.ok(warehouseCapacityProjection.getAllWarehousesWithCapacityInfo());
    }
}
