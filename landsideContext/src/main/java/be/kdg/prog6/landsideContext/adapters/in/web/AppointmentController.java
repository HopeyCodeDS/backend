package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.ScheduleAppointmentRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.AppointmentMapper;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import be.kdg.prog6.landsideContext.ports.in.ScheduleAppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/landside/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    
    private final ScheduleAppointmentUseCase scheduleAppointmentUseCase;
    private final AppointmentMapper appointmentMapper;
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> scheduleAppointment(
            @RequestBody ScheduleAppointmentRequestDto requestDto) {
        try {
            ScheduleAppointmentCommand command = appointmentMapper.toCommand(requestDto);
            UUID appointmentId = scheduleAppointmentUseCase.scheduleAppointment(command);
            
            return ResponseEntity.ok(Map.of(
                "appointmentId", appointmentId,
                "message", "Appointment scheduled successfully"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Validation error",
                "message", e.getMessage()
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Business rule violation",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Internal server error",
                "message", "An unexpected error occurred"
            ));
        }
    }
} 