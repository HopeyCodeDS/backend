package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class ConveyorBeltAssignedEvent {
    private String licensePlate;
    private String conveyorBeltId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    private LocalDateTime assignedAt;


    // Default no-arg constructor
    public ConveyorBeltAssignedEvent() {}

    // Constructor with all fields
    @JsonCreator
    public ConveyorBeltAssignedEvent(
            @JsonProperty("licensePlate") String licensePlate,
            @JsonProperty("conveyorBeltId") String conveyorBeltId,
            @JsonProperty("assignedAt") LocalDateTime assignedAt
    ) {
        this.licensePlate = licensePlate;
        this.conveyorBeltId = conveyorBeltId;
        this.assignedAt = assignedAt;
    }

    public String getLicensePlate() { return licensePlate; }
    public String getConveyorBeltId() { return conveyorBeltId; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
}
