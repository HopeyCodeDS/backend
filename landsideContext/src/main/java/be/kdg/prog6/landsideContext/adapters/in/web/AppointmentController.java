package be.kdg.prog6.landsideContext.adapters.in.web;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.*;
import be.kdg.prog6.landsideContext.adapters.in.web.mapper.AppointmentMapper;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.ArrivalWindow;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import be.kdg.prog6.landsideContext.domain.commands.UpdateAppointmentCommand;
import be.kdg.prog6.landsideContext.ports.in.ScheduleAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.in.GetAllAppointmentsUseCase;
import be.kdg.prog6.landsideContext.ports.in.GetAppointmentsByStatusUseCase;
import be.kdg.prog6.landsideContext.ports.in.GetTodaysAppointmentsUseCase;
import be.kdg.prog6.landsideContext.ports.in.UpdateAppointmentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AppointmentController {
    
    private final ScheduleAppointmentUseCase scheduleAppointmentUseCase;
    private final GetAppointmentUseCase getAppointmentUseCase;
    private final GetAllAppointmentsUseCase getAllAppointmentsUseCase;
    private final GetAppointmentsByStatusUseCase getAppointmentsByStatusUseCase;
    private final GetTodaysAppointmentsUseCase getTodaysAppointmentsUseCase;
    private final AppointmentMapper appointmentMapper;
    private final UpdateAppointmentUseCase updateAppointmentUseCase;
    
    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<AppointmentResponseDto> scheduleAppointment(
            @RequestBody ScheduleAppointmentRequestDto requestDto) {
        try {
            ScheduleAppointmentCommand command = appointmentMapper.toCommand(requestDto);

            // Load the created aggregate appointment to access calculated fields
            Appointment populatedAppointment = scheduleAppointmentUseCase.scheduleAppointment(command);

            if (populatedAppointment == null) {
                return ResponseEntity.internalServerError().build();
            }

            // Convert ArrivalWindow to DTO to avoid circular references
            ArrivalWindowResponseDto arrivalWindowDto = convertToArrivalWindowDto(populatedAppointment.getArrivalWindow());
            
            // Create proper response DTO
            AppointmentResponseDto response = new AppointmentResponseDto(
                populatedAppointment.getAppointmentId(),
                requestDto.getSellerId(),
                requestDto.getSellerName(),
                requestDto.getLicensePlate(),
                requestDto.getTruckType().name(),
                requestDto.getRawMaterialName(),
                populatedAppointment.getStatus(),
                requestDto.getScheduledTime(),
                arrivalWindowDto
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error scheduling appointment: {}", e.getMessage());
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
                convertToArrivalWindowDto(appointment.getArrivalWindow())
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
                    convertToArrivalWindowDto(appointment.getArrivalWindow())
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
                    convertToArrivalWindowDto(appointment.getArrivalWindow())
                ))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<AppointmentResponseDto>> getTodaysAppointments() {
        try {
            List<Appointment> appointments = getTodaysAppointmentsUseCase.getTodaysAppointments();
            
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
                    convertToArrivalWindowDto(appointment.getArrivalWindow())
                ))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }

    @PutMapping("/{appointmentId}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')") 
    public ResponseEntity<AppointmentResponseDto> updateAppointment(
            @PathVariable UUID appointmentId,
            @RequestBody UpdateAppointmentRequestDto requestDto) {
        try {
            UpdateAppointmentCommand command = appointmentMapper.toUpdateCommand(appointmentId, requestDto);
            Appointment updatedAppointment = updateAppointmentUseCase.updateAppointment(command);
            
            // Create response DTO
            AppointmentResponseDto response = new AppointmentResponseDto(
                updatedAppointment.getAppointmentId(),
                updatedAppointment.getSellerId(),
                updatedAppointment.getSellerName(),
                updatedAppointment.getTruck().getLicensePlate().getValue(),
                updatedAppointment.getTruck().getTruckType().name(),
                updatedAppointment.getRawMaterial().getName(),
                updatedAppointment.getStatus(),
                updatedAppointment.getScheduledTime(),
                convertToArrivalWindowDto(updatedAppointment.getArrivalWindow())
            );
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Invalid request for updating appointment: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error updating appointment: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private ArrivalWindowResponseDto convertToArrivalWindowDto(ArrivalWindow arrivalWindow) {
        List<AppointmentSummaryDto> appointmentSummaries = arrivalWindow.getAppointments().stream()
                .map(appointment -> new AppointmentSummaryDto(
                        appointment.getAppointmentId(),
                        appointment.getSellerName(),
                        appointment.getTruck().getLicensePlate().getValue(),
                        appointment.getTruck().getTruckType().name(),
                        appointment.getRawMaterial().getName(),
                        appointment.getStatus(),
                        appointment.getScheduledTime()
                ))
                .collect(Collectors.toList());

        return new ArrivalWindowResponseDto(
                arrivalWindow.getStartTime(),
                arrivalWindow.getEndTime(),
                appointmentSummaries
        );
    }
} 