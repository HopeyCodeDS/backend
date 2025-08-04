package be.kdg.prog6.watersideContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ShipCaptainOperationsOverview {
    private final UUID shippingOrderId;
    private final String vesselNumber;
    private final String inspectionStatus;
    private final String bunkeringStatus;
    private final boolean canLeavePort;
    private final LocalDateTime inspectionCompletedDate;
    private final LocalDateTime bunkeringCompletedDate;
    
    public ShipCaptainOperationsOverview(UUID shippingOrderId, String vesselNumber, 
                                       String inspectionStatus, String bunkeringStatus,
                                       LocalDateTime inspectionCompletedDate, 
                                       LocalDateTime bunkeringCompletedDate) {
        this.shippingOrderId = shippingOrderId;
        this.vesselNumber = vesselNumber;
        this.inspectionStatus = inspectionStatus;
        this.bunkeringStatus = bunkeringStatus;
        this.inspectionCompletedDate = inspectionCompletedDate;
        this.bunkeringCompletedDate = bunkeringCompletedDate;
        
        // Can leave if both operations are completed
        this.canLeavePort = "COMPLETED".equals(inspectionStatus) && "COMPLETED".equals(bunkeringStatus);
    }
} 