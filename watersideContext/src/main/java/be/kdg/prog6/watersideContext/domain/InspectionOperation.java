package be.kdg.prog6.watersideContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class InspectionOperation {
    private LocalDateTime plannedDate;
    private LocalDateTime completedDate;
    private String inspectorSignature;
    private InspectionStatus status;

    public InspectionOperation() {
        this.plannedDate = LocalDateTime.now().plusHours(2); // Planned inspection 2 hours after actual arrival date of the vessel
        this.status = InspectionStatus.PLANNED;
    }

    public void completeInspection(String inspectorSignature) {
        this.completedDate = LocalDateTime.now();
        this.inspectorSignature = inspectorSignature;
        this.status = InspectionStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return status == InspectionStatus.COMPLETED;
    }

    public enum InspectionStatus {
        PLANNED, IN_PROGRESS, COMPLETED
    }
} 