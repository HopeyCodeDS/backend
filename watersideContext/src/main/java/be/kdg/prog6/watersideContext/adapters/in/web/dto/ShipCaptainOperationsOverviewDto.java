package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ShipCaptainOperationsOverviewDto {
    private UUID shippingOrderId;
    private String vesselNumber;
    private String inspectionStatus;
    private String bunkeringStatus;
    private boolean canLeavePort;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime inspectionCompletedDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime bunkeringCompletedDate;
} 