package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.ports.out.GateControlPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GateControlAdapter implements GateControlPort {
    
    private boolean isGateOpen = false;
    
    @Override
    public void openGate() {
        log.info("🚪 Opening gate for recognized truck");
        isGateOpen = true;
        // In a real implementation, this would send a signal to the physical gate
        // For now, we just log the action
    }
    
    @Override
    public void closeGate() {
        log.info("🚪 Closing gate");
        isGateOpen = false;
        // In a real implementation, this would send a signal to the physical gate
    }
    
    @Override
    public boolean isGateOpen() {
        return isGateOpen;
    }
} 