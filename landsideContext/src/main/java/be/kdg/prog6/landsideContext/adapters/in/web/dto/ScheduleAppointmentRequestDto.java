package be.kdg.prog6.landsideContext.adapters.in.web.dto;

import be.kdg.prog6.landsideContext.domain.Truck;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ScheduleAppointmentRequestDto {
    private UUID sellerId;
    private String sellerName;
    private String licensePlate;
    private Truck.TruckType truckType;
    private String rawMaterialName;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime scheduledTime;
}
