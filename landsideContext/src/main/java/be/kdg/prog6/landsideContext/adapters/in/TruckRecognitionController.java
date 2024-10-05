package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.RecognizeTruckUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trucks")
public class TruckRecognitionController {

    private final static Logger log = LoggerFactory.getLogger(TruckRecognitionController.class);

    private final RecognizeTruckUseCase recognizeTruckUseCase;

    public TruckRecognitionController(RecognizeTruckUseCase recognizeTruckUseCase) {
        this.recognizeTruckUseCase = recognizeTruckUseCase;
    }

    @GetMapping("/recognize/{licensePlate}")
    public ResponseEntity<String> recognizeTruck(@PathVariable String licensePlate) {
        log.info("Received request to recognize truck with license plate: {}", licensePlate);
        Optional<Appointment> appointmentOpt = recognizeTruckUseCase.recognizeTruck(licensePlate);

        if (appointmentOpt.isPresent()) {
            // Temporary logic to open the gate
            openGate(appointmentOpt.get());
            return ResponseEntity.ok("Gate opened for truck with license plate: " + licensePlate);
        } else {
            return ResponseEntity.status(404).body("No appointment found for truck with license plate: " + licensePlate);
        }
    }

    private void openGate(Appointment appointment) {
        // Logic to open the gate. I need to consult the lecturer of any better way to implement this.
        System.out.println("Opening gate for truck with license plate: " + appointment.getTruck().getLicensePlate());
    }
}
