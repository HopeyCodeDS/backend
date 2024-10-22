package be.kdg.prog6.landsideContext.adapters.in;


import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentCommand;
import be.kdg.prog6.landsideContext.facade.AppointmentFacade;
import be.kdg.prog6.landsideContext.domain.Appointment;

import java.util.List;
import java.util.Optional;

import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentFacade appointmentFacade;
    private final CreateAppointmentUseCase createAppointmentUseCase;

    public AppointmentController(AppointmentFacade appointmentFacade, CreateAppointmentUseCase createAppointmentUseCase) {
        this.appointmentFacade = appointmentFacade;
        this.createAppointmentUseCase = createAppointmentUseCase;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody CreateAppointmentCommand command) {
        Appointment appointment = createAppointmentUseCase.createAppointment(command);
        log.info("Created appointment: {}", appointment);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @GetMapping("/supplier/{sellerId}")
    public ResponseEntity<Appointment> getAppointmentBySellerId(@PathVariable UUID sellerId) {
        Optional<Appointment> appointmentOpt = appointmentFacade.getAppointmentBySellerId(sellerId);
        log.info("This is the SellerId: {}", sellerId);

        return appointmentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/supplier/{sellerId}/material/{materialType}")
    public ResponseEntity<Appointment> getAppointmentBySellerIdAndMaterialType(
            @PathVariable UUID sellerId, @PathVariable String materialType) {

        Optional<Appointment> appointmentOpt = appointmentFacade.getAppointmentBySellerIdAndMaterialType(sellerId, materialType);

        return appointmentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/truck/{licensePlate}")
    public ResponseEntity<List<Appointment>> getAppointmentsByTruckLicensePlate(@PathVariable String licensePlate) {
        List<Appointment> appointments = appointmentFacade.getAppointmentsByTruckLicensePlate(licensePlate);
        return ResponseEntity.ok(appointments);
    }
//    @GetMapping("/arrival-window")
//    public ResponseEntity<List<Appointment>> getAppointmentsDuringArrivalWindow(
//            @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
//
//        List<Appointment> appointments = appointmentFacade.getAppointmentsDuringArrivalWindow(start, end);
//        return ResponseEntity.ok(appointments);
//    }
}
