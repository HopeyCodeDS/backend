//package be.kdg.prog6.landsideContext.adapters.in;
//
//import be.kdg.prog6.landsideContext.adapters.in.dto.TruckDTO;
//import be.kdg.prog6.landsideContext.domain.Truck;
//import be.kdg.prog6.landsideContext.ports.in.ScheduleTruckArrivalUseCase;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//
//
//@RestController
//@RequestMapping
//public class TruckController {
//    private final static Logger log = LoggerFactory.getLogger(TruckRecognitionController.class);
//
//    private ScheduleTruckArrivalUseCase scheduleTruckArrivalUseCase;
//
//    public TruckController(ScheduleTruckArrivalUseCase scheduleTruckArrivalUseCase) {
//        this.scheduleTruckArrivalUseCase = scheduleTruckArrivalUseCase;
//    }
//
//    @PostMapping("/schedule/appointments")
//    public ResponseEntity<String> scheduleAppointment(@RequestBody TruckDTO truckDTO, @RequestParam LocalDateTime arrivalTime) {
//        Truck truck = new Truck(truckDTO.getLicensePlate(), truckDTO.getWeight());
//        try {
//            scheduleTruckArrivalUseCase.scheduleTruckArrival(truck, arrivalTime);
//            return ResponseEntity.ok("Truck appointment scheduled successfully.");
//        } catch (IllegalStateException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//}
