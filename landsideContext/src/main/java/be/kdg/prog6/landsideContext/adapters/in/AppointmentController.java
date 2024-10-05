package be.kdg.prog6.landsideContext.adapters.in;


import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.events.AppointmentCreatedEvent;
import be.kdg.prog6.common.facades.CreateAppointmentCommand;
import be.kdg.prog6.landsideContext.core.CreateAppointmentUseCaseImpl;
import be.kdg.prog6.landsideContext.facade.AppointmentFacade;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.facade.AppointmentFacade;
import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentUseCase;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentFacade appointmentFacade;

    public AppointmentController(AppointmentFacade appointmentFacade) {
        this.appointmentFacade = appointmentFacade;
    }

    @PostMapping
    public ResponseEntity<AppointmentCreatedEvent> createAppointment(@RequestBody CreateAppointmentCommand command) {

        AppointmentCreatedEvent appointmentCreatedEvent = appointmentFacade.createAppointment(command);
        return ResponseEntity.ok(appointmentCreatedEvent);
    }

    @GetMapping("/supplier/{sellerId}")
    public ResponseEntity<Appointment> getAppointmentBySellerId(@PathVariable UUID sellerId) {
        Optional<Appointment> appointmentOpt = appointmentFacade.getAppointmentBySellerId(sellerId);

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
    @GetMapping("/arrival-window")
    public ResponseEntity<List<Appointment>> getAppointmentsDuringArrivalWindow(
            @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {

        List<Appointment> appointments = appointmentFacade.getAppointmentsDuringArrivalWindow(start, end);
        return ResponseEntity.ok(appointments);
    }
}
