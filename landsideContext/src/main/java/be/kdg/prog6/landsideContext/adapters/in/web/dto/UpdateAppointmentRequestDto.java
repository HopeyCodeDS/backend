package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.Truck.TruckType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateAppointmentRequestDto {
    private UUID sellerId;
    private String licensePlate;
    private TruckType truckType;
    private String rawMaterialName;
    private AppointmentStatus status;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime scheduledTime;
}
