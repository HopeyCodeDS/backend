package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.ports.out.GateControlPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GateControlAdapter implements GateControlPort {
    private static final Logger log = LoggerFactory.getLogger(GateControlAdapter.class);
    @Override
    public void openGate(String licensePlate) {
        log.info("Opening gate for truck with license plate: {}", licensePlate);

    }
}
