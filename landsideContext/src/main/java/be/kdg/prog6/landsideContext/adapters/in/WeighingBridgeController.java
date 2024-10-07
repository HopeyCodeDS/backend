package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.landsideContext.adapters.in.dto.WeighTruckRequestDTO;
import be.kdg.prog6.landsideContext.core.GetWeighingBridgeNumberUseCaseImpl;
import be.kdg.prog6.landsideContext.core.WeighTruckUseCaseImpl;
import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;
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

    @PostMapping("/weigh")
    public ResponseEntity<String> weighTruck(@RequestParam String licensePlate,
                                             @RequestParam double weight) {
        String warehouseNumber = weighTruckUseCaseImpl.weighTruck(licensePlate, weight);
        return ResponseEntity.ok(warehouseNumber);
    }


    @PostMapping("/generate-ticket")
    public ResponseEntity<WeighBridgeTicket> generateWeighbridgeTicket(@RequestBody WeighTruckRequestDTO request) {
        WeighBridgeTicket ticket = weighTruckUseCaseImpl.generateWeighBridgeTicket(
                request.getLicensePlate(),
                request.getGrossWeight(),
                request.getTareWeight()
        );
        return ResponseEntity.ok(ticket);
    }
}
