package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.landsideContext.core.GetWeighingBridgeNumberUseCaseImpl;
import be.kdg.prog6.landsideContext.core.WeighTruckUseCaseImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weighing-bridge")
public class WeighingBridgeController {

    private final GetWeighingBridgeNumberUseCaseImpl getWeighingBridgeNumberUseCaseImpl;
    private final WeighTruckUseCaseImpl weighTruckUseCaseImpl;

    public WeighingBridgeController(GetWeighingBridgeNumberUseCaseImpl getWeighingBridgeNumberUseCaseImpl, WeighTruckUseCaseImpl weighTruckUseCaseImpl) {
        this.getWeighingBridgeNumberUseCaseImpl = getWeighingBridgeNumberUseCaseImpl;
        this.weighTruckUseCaseImpl = weighTruckUseCaseImpl;
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<String> getWeighingBridgeNumber(@PathVariable String licensePlate) {
        try {
            String bridgeNumber = getWeighingBridgeNumberUseCaseImpl.getWeighingBridgeNumber(licensePlate);
            return ResponseEntity.ok("Weighing Bridge Number: " + bridgeNumber);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/weigh/{licensePlate}")
    public ResponseEntity<String> weighTruck(@PathVariable String licensePlate, @RequestParam double weight) {
        try {
            String warehouseNumber = weighTruckUseCaseImpl.weighTruck(licensePlate, weight);
            return ResponseEntity.ok("Truck weighed successfully. Assigned to: " + warehouseNumber);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
