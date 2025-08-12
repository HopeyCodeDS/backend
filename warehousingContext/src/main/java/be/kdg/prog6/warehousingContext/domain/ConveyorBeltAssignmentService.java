package be.kdg.prog6.warehousingContext.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConveyorBeltAssignmentService {
    
    public String assignConveyorBelt(String rawMaterialName) {
        String conveyorBelt = switch (rawMaterialName.toLowerCase()) {
            case "gypsum" -> "Conveyor-1";
            case "iron_ore" -> "Conveyor-2";
            case "cement" -> "Conveyor-3";
            case "petcoke" -> "Conveyor-4";
            case "slag" -> "Conveyor-5";
            default -> "Conveyor-General";
        };
        
        log.debug("Assigned conveyor belt: {} for material: {}", conveyorBelt, rawMaterialName);
        return conveyorBelt;
    }
} 