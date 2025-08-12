package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.ScheduleAppointmentRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.AppointmentResponseDto;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.AppointmentMapper;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import be.kdg.prog6.landsideContext.ports.in.ScheduleAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.in.GetAllAppointmentsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/landside/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    
    private final ScheduleAppointmentUseCase scheduleAppointmentUseCase;
    private final GetAppointmentUseCase getAppointmentUseCase;
    private final GetAllAppointmentsUseCase getAllAppointmentsUseCase;
    private final AppointmentMapper appointmentMapper;
    
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> scheduleAppointment(
            @RequestBody ScheduleAppointmentRequestDto requestDto) {
        try {
            ScheduleAppointmentCommand command = appointmentMapper.toCommand(requestDto);
            UUID appointmentId = scheduleAppointmentUseCase.scheduleAppointment(command);
            
            // Create proper response DTO
            AppointmentResponseDto response = new AppointmentResponseDto(
                appointmentId,
                requestDto.getSellerId(),
                requestDto.getLicensePlate(),
                requestDto.getTruckType().name(),
                requestDto.getRawMaterialName(),
                AppointmentStatus.SCHEDULED,
                requestDto.getScheduledTime()
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            AppointmentResponseDto errorResponse = new AppointmentResponseDto(
                null, null, null, null, null, null, null
            );
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (IllegalStateException e) {
            AppointmentResponseDto errorResponse = new AppointmentResponseDto(
                null, null, null, null, null, null, null
            );
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            AppointmentResponseDto errorResponse = new AppointmentResponseDto(
                null, null, null, null, null, null, null
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> getAppointment(@PathVariable UUID appointmentId) {
        try {
            Appointment appointment = getAppointmentUseCase.getAppointment(appointmentId);
            
            if (appointment == null) {
                AppointmentResponseDto notFoundResponse = new AppointmentResponseDto(
                    null, null, null, null, null, null, null
                );
                return ResponseEntity.notFound().build();
            }
            
            AppointmentResponseDto response = new AppointmentResponseDto(
                appointment.getAppointmentId(),
                appointment.getSellerId(),
                appointment.getTruck().getLicensePlate().getValue(),
                appointment.getTruck().getTruckType().name(),
                appointment.getRawMaterial().getName(),
                appointment.getStatus(),
                appointment.getScheduledTime()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            AppointmentResponseDto errorResponse = new AppointmentResponseDto(
                null, null, null, null, null, null, null
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        try {
            List<Appointment> appointments = getAllAppointmentsUseCase.getAllAppointments();
            
            List<AppointmentResponseDto> responseDtos = appointments.stream()
                .map(appointment -> new AppointmentResponseDto(
                    appointment.getAppointmentId(),
                    appointment.getSellerId(),
                    appointment.getTruck().getLicensePlate().getValue(),
                    appointment.getTruck().getTruckType().name(),
                    appointment.getRawMaterial().getName(),
                    appointment.getStatus(),
                    appointment.getScheduledTime()
                ))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }
} 