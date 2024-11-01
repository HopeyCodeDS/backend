package be.kdg.prog6.landsideContext.adapters.in;

import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;
import be.kdg.prog6.landsideContext.facade.GenerateTicketCommand;
import be.kdg.prog6.landsideContext.ports.in.GenerateWeighBridgeTicketUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/weighbridge")
public class WeighBridgeTicketController {
    private final GenerateWeighBridgeTicketUseCase generateTicketUseCase;

    public WeighBridgeTicketController(GenerateWeighBridgeTicketUseCase generateTicketUseCase) {
        this.generateTicketUseCase = generateTicketUseCase;
    }

    @PostMapping("/generate-ticket")
    public ResponseEntity<WeighBridgeTicket> generateTicket(@RequestBody GenerateTicketCommand command) {
        log.info("Generating ticket");
        WeighBridgeTicket ticket = generateTicketUseCase.generateTicket(command);
        return ResponseEntity.ok(ticket);
    }
}
