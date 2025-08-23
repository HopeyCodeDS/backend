package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.TruckRecognitionRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.GateMapper;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.commands.RecognizeTruckCommand;
import be.kdg.prog6.landsideContext.ports.in.RecognizeTruckUseCase;
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentByLicensePlateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/landside")
@RequiredArgsConstructor
public class GateController {
    
    private final RecognizeTruckUseCase recognizeTruckUseCase;
    private final GetAppointmentByLicensePlateUseCase getAppointmentByLicensePlateUseCase;
    private final GateMapper gateMapper;
    
    @PostMapping("/gate/recognize")
    @PreAuthorize("hasRole('TRUCK_DRIVER')")
    public ResponseEntity<Map<String, Object>> recognizeTruck(@RequestBody TruckRecognitionRequestDto requestDto) {
        try {
            RecognizeTruckCommand command = gateMapper.toRecognizeTruckCommand(requestDto);
            boolean recognized = recognizeTruckUseCase.recognizeTruck(command);
            
            if (recognized) {
                // Get appointment details for response using use case
                Optional<Appointment> appointmentOpt = getAppointmentByLicensePlateUseCase
                    .getAppointmentByLicensePlate(requestDto.getLicensePlate());
                
                if (appointmentOpt.isPresent()) {
                    Appointment appointment = appointmentOpt.get();
                    return ResponseEntity.ok(Map.of(
                        "recognized", true,
                        "message", "Truck recognized. Gate opened.",
                        "appointmentId", appointment.getAppointmentId(),
                        "sellerId", appointment.getSellerId(),
                        "rawMaterialName", appointment.getRawMaterial().getName(),
                        "arrivalWindow", appointment.getArrivalWindow().toString()
                    ));
                }
            }
            
            return ResponseEntity.ok(Map.of(
                "recognized", false,
                "message", "Truck not recognized. No valid appointment found or outside arrival window."
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Gate recognition error",
                "message", e.getMessage()
            ));
        }
    }
}