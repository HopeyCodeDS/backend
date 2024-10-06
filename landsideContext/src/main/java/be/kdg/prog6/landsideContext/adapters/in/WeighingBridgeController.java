package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.landsideContext.core.GetWeighingBridgeNumberUseCaseImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weighing-bridge")
public class WeighingBridgeController {

    private final GetWeighingBridgeNumberUseCaseImpl getWeighingBridgeNumberUseCaseImpl;

    public WeighingBridgeController(GetWeighingBridgeNumberUseCaseImpl getWeighingBridgeNumberUseCaseImpl) {
        this.getWeighingBridgeNumberUseCaseImpl = getWeighingBridgeNumberUseCaseImpl;
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
}
