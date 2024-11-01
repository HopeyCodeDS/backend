package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.landsideContext.ports.in.DockTruckUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@RestController
@RequestMapping("/docking")
public class DockingController {

    private final DockTruckUseCase dockTruckUseCase;

    @Autowired
    public DockingController(DockTruckUseCase dockTruckUseCase) {
        this.dockTruckUseCase = dockTruckUseCase;
    }

    @PostMapping("/dock/{licensePlate}")
    public ResponseEntity<String> dockTruck(@PathVariable String licensePlate) {
        try {
            // Call the use case to process the docking
            dockTruckUseCase.dockTruck(licensePlate);
            return ResponseEntity.ok("Truck docked successfully: " + licensePlate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while docking the truck: " + e.getMessage());
        }
    }
}
