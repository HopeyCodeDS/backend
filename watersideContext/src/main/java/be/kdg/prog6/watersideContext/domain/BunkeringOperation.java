package be.kdg.prog6.watersideContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BunkeringOperation {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime plannedDate;
    @Setter
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime completedDate;
    @Setter
    private BunkeringStatus status;
    @Setter
    private String bunkeringOfficerSignature;

    public BunkeringOperation() {
        // Default constructor
        this.status = BunkeringStatus.PLANNED;
    }

    public BunkeringOperation(LocalDateTime actualArrivalDate) {
        this.plannedDate = actualArrivalDate.plusHours(4); // Planned bunkering 4 hours after actual arrival date of the vessel
        this.status = BunkeringStatus.PLANNED;
    }

    public void setPlannedDate(LocalDateTime actualArrivalDate) {
        this.plannedDate = actualArrivalDate.plusHours(4); // Planned bunkering 4 hours after actual arrival date of the vessel
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

    public String getStatusDescription() {
        return this.status.name();
    }

}