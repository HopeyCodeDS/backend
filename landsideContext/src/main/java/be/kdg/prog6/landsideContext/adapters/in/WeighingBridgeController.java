package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.landsideContext.adapters.in.dto.WeighTruckRequestDTO;
import be.kdg.prog6.landsideContext.core.WeighTruckAndAssignWarehouseUseCaseImpl;
import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;
import be.kdg.prog6.landsideContext.ports.in.AssignWeighingBridgeNumberUseCase;
import be.kdg.prog6.landsideContext.ports.in.WeighTruckAndAssignWarehouseUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weighing-bridge")
public class WeighingBridgeController {
    private static final Logger log = LoggerFactory.getLogger(WeighingBridgeController.class);

    private final AssignWeighingBridgeNumberUseCase assignWeighingBridgeNumberUseCase;
    private final WeighTruckAndAssignWarehouseUseCase weighTruckAndAssignWarehouseUseCase;
    public WeighingBridgeController(AssignWeighingBridgeNumberUseCase assignWeighingBridgeNumberUseCase, WeighTruckAndAssignWarehouseUseCase weighTruckAndAssignWarehouseUseCase) {
        this.assignWeighingBridgeNumberUseCase = assignWeighingBridgeNumberUseCase;
        this.weighTruckAndAssignWarehouseUseCase = weighTruckAndAssignWarehouseUseCase;
    }

    @GetMapping("/assign-bridge/{licensePlate}")
    public ResponseEntity<String> getWeighingBridgeNumber(@PathVariable String licensePlate) {
        log.info("Getting weighing bridge for truck with license plate: {}", licensePlate);

        String bridgeNumber = assignWeighingBridgeNumberUseCase.assignWeighingBridgeNumberToTruck(licensePlate);
        if (bridgeNumber != null) {
            log.info("Got the weighing bridge number {} to truck with license plate: {}", bridgeNumber, licensePlate);
            return ResponseEntity.ok("Weighing bridge " + bridgeNumber + " assigned to truck with license plate: " + licensePlate);
        } else {
            log.warn("No valid appointment or bridge assignment could be made for truck with license plate: {}", licensePlate);
            return ResponseEntity.status(404).body("No valid appointment or bridge assignment could be made for truck with license plate: " + licensePlate);
        }
    }

    @PostMapping("/weigh")
    public ResponseEntity<String> weighTruckAndAssignWarehouse(
            @RequestParam String licensePlate,
            @RequestParam double weight) {

        log.info("Weighing truck with license plate: {} and weight: {}", licensePlate, weight);

        try {
            String warehouseNumber = weighTruckAndAssignWarehouseUseCase.weighAndAssignWarehouseToTruck(licensePlate, weight);
            log.info("Truck {} assigned to warehouse number {}", licensePlate, warehouseNumber);
            return ResponseEntity.ok("Truck with license plate " + licensePlate + " assigned to warehouse " + warehouseNumber);
        } catch (IllegalArgumentException e) {
            log.warn("Error processing truck {}: {}", licensePlate, e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PostMapping("/generate-ticket")
    public ResponseEntity<WeighBridgeTicket> generateWeighbridgeTicket(@RequestBody WeighTruckRequestDTO request) {
        WeighBridgeTicket ticket = weighTruckAndAssignWarehouseUseCase.generateWeighBridgeTicket(
                request.getLicensePlate(),
                request.getGrossWeight(),
                request.getTareWeight()
        );
        return ResponseEntity.ok(ticket);
    }
}
