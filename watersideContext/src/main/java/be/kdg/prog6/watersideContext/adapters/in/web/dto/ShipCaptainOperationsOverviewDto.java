package be.kdg.prog6.watersideContext.adapters.in.web.dto;

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
    private LocalDateTime inspectionCompletedDate;
    private LocalDateTime bunkeringCompletedDate;
} 