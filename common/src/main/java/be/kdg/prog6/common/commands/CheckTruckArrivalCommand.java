package be.kdg.prog6.common.commands;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckTruckArrivalCommand {
    private String warehouseId;
    private String appointmentId;
    private LocalDateTime arrivalTime;

    public CheckTruckArrivalCommand(String warehouseId, String appointmentId, LocalDateTime arrivalTime) {
        this.warehouseId = warehouseId;
        this.appointmentId = appointmentId;
        this.arrivalTime = arrivalTime;
    }
}