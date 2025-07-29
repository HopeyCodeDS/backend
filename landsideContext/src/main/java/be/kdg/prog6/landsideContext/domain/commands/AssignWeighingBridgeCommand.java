package be.kdg.prog6.landsideContext.domain.commands;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AssignWeighingBridgeCommand {
    private final UUID commandId;
    private final String licensePlate;
    private final LocalDateTime assignmentTime;
    
    public AssignWeighingBridgeCommand(String licensePlate, LocalDateTime assignmentTime) {
        this.commandId = UUID.randomUUID();
        this.licensePlate = licensePlate;
        this.assignmentTime = assignmentTime;
    }
} 