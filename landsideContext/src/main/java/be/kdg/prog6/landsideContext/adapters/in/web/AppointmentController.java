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
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentsByStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final GetAppointmentsByStatusUseCase getAppointmentsByStatusUseCase;
    private final AppointmentMapper appointmentMapper;
    
    @PostMapping
    @PreAuthorize("hasRole('SELLER')") 
    public ResponseEntity<AppointmentResponseDto> scheduleAppointment(
            @RequestBody ScheduleAppointmentRequestDto requestDto) {
        try {
            ScheduleAppointmentCommand command = appointmentMapper.toCommand(requestDto);
            UUID appointmentId = scheduleAppointmentUseCase.scheduleAppointment(command);

            // Load the created aggregate appointment to access calculated fields
            Appointment appointment = getAppointmentUseCase.getAppointment(appointmentId);

            if (appointment == null) {
                return ResponseEntity.internalServerError().build();
            }
            
            // Create proper response DTO
            AppointmentResponseDto response = new AppointmentResponseDto(
                appointmentId,
                requestDto.getSellerId(),
                requestDto.getSellerName(),
                requestDto.getLicensePlate(),
                requestDto.getTruckType().name(),
                requestDto.getRawMaterialName(),
                AppointmentStatus.SCHEDULED,
                requestDto.getScheduledTime(),
                appointment.getArrivalWindow()
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')") 
    public ResponseEntity<AppointmentResponseDto> getAppointment(@PathVariable UUID appointmentId) {
        try {
            Appointment appointment = getAppointmentUseCase.getAppointment(appointmentId);
            
            if (appointment == null) {
                return ResponseEntity.notFound().build();
            }
            
            AppointmentResponseDto response = new AppointmentResponseDto(
                appointment.getAppointmentId(),
                appointment.getSellerId(),
                appointment.getSellerName(),
                appointment.getTruck().getLicensePlate().getValue(),
                appointment.getTruck().getTruckType().name(),
                appointment.getRawMaterial().getName(),
                appointment.getStatus(),
                appointment.getScheduledTime(),
                appointment.getArrivalWindow()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')") 
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        try {
            List<Appointment> appointments = getAllAppointmentsUseCase.getAllAppointments();
            
            List<AppointmentResponseDto> responseDtos = appointments.stream()
                .map(appointment -> new AppointmentResponseDto(
                    appointment.getAppointmentId(),
                    appointment.getSellerId(),
                    appointment.getSellerName(),
                    appointment.getTruck().getLicensePlate().getValue(),
                    appointment.getTruck().getTruckType().name(),
                    appointment.getRawMaterial().getName(),
                    appointment.getStatus(),
                    appointment.getScheduledTime(),
                    appointment.getArrivalWindow()
                ))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }

    // Generic endpoint for any status
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')") 
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByStatus(@PathVariable AppointmentStatus status) {
        try {
            List<Appointment> appointments = getAppointmentsByStatusUseCase.getAppointmentsByStatus(status);
            
            List<AppointmentResponseDto> responseDtos = appointments.stream()
                .map(appointment -> new AppointmentResponseDto(
                    appointment.getAppointmentId(),
                    appointment.getSellerId(),
                    appointment.getSellerName(),
                    appointment.getTruck().getLicensePlate().getValue(),
                    appointment.getTruck().getTruckType().name(),
                    appointment.getRawMaterial().getName(),
                    appointment.getStatus(),
                    appointment.getScheduledTime(),
                    appointment.getArrivalWindow()
                ))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }
} 