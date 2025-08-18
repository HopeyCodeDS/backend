package be.kdg.prog6.watersideContext.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class InspectionOperation {
    private LocalDateTime plannedDate;
    private LocalDateTime completedDate;
    private String inspectorSignature;
    private InspectionStatus status;

    public InspectionOperation() {
        this.status = InspectionStatus.PLANNED;
    }

    public InspectionOperation(LocalDateTime actualArrivalDate) {
        this.plannedDate = actualArrivalDate.plusHours(2); // Planned inspection 2 hours after actual arrival date of the vessel
        this.status = InspectionStatus.PLANNED;
    }

    public void setPlannedDate(LocalDateTime actualArrivalDate) {
        this.plannedDate = actualArrivalDate.plusHours(2); // Planned inspection 2 hours after actual arrival date of the vessel
    }

    public void completeInspection(String inspectorSignature) {
        this.completedDate = LocalDateTime.now();
        this.inspectorSignature = inspectorSignature;
        this.status = InspectionStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return status == InspectionStatus.COMPLETED;
    }

    public void setStatus(InspectionStatus status) {
        this.status = status;
    }
    
    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }
    
    public void setInspectorSignature(String inspectorSignature) {
        this.inspectorSignature = inspectorSignature;
    }

    public String getStatusDescription() {
        return this.status.name();
    }

    public enum InspectionStatus {
        PLANNED, IN_PROGRESS, COMPLETED
    }
} 