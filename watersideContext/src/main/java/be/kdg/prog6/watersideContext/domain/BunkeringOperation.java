package be.kdg.prog6.watersideContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BunkeringOperation {
    private LocalDateTime plannedDate;
    private LocalDateTime completedDate;
    private BunkeringStatus status;
    private String bunkeringOfficerSignature;

    public BunkeringOperation() {
        this.plannedDate = LocalDateTime.now().plusHours(4); // Planned bunkering 4 hours after actual arrival date of the vessel
        this.status = BunkeringStatus.PLANNED;
    }

    public void completeBunkering(String bunkeringOfficerSignature) {
        this.completedDate = LocalDateTime.now();
        this.bunkeringOfficerSignature = bunkeringOfficerSignature;
        this.status = BunkeringStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return status == BunkeringStatus.COMPLETED;
    }

    public enum BunkeringStatus {
        PLANNED, IN_PROGRESS, COMPLETED
    }
} 