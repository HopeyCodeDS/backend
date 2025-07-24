package be.kdg.prog6.landsideContext.domain.commands;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class RecognizeTruckCommand {
    private final UUID commandId;
    private final String licensePlate;
    private final LocalDateTime recognitionTime;
    
    public RecognizeTruckCommand(String licensePlate, LocalDateTime recognitionTime) {
        this.commandId = UUID.randomUUID();
        this.licensePlate = licensePlate;
        this.recognitionTime = recognitionTime;
    }
} 