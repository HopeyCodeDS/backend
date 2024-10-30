package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.landsideContext.ports.in.DockTruckAndGeneratePDTUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class DockTruckController {

    private final DockTruckAndGeneratePDTUseCase dockTruckUseCase;

    public DockTruckController(DockTruckAndGeneratePDTUseCase dockTruckUseCase) {
        this.dockTruckUseCase = dockTruckUseCase;
    }
    /**
     * Endpoint to dock a truck and initiate payload delivery.
     * Expects a truck's license plate to be passed as a parameter.
     *
     * @param licensePlate The license plate of the truck to be docked.
     * @return ResponseEntity with success or error message.
     */

    @PostMapping("/dock")
    public ResponseEntity<String> dockTruck(@RequestParam String licensePlate) {
        try {
            dockTruckUseCase.dockTruckAndGeneratePDT(licensePlate);
            return ResponseEntity.ok("Truck docked and payload delivery initiated for license plate: " + licensePlate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
