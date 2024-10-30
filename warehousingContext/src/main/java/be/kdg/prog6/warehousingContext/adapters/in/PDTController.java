package be.kdg.prog6.warehousingContext.adapters.in;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.out.PayloadDeliveryRecordRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pdt")
public class PDTController {

    private static final Logger log = LoggerFactory.getLogger(PDTController.class);

    private final PayloadDeliveryRecordRepositoryPort payloadDeliveryRecordRepositoryPort;

    public PDTController(PayloadDeliveryRecordRepositoryPort payloadDeliveryRecordRepositoryPort) {
        this.payloadDeliveryRecordRepositoryPort = payloadDeliveryRecordRepositoryPort;
    }

    // Retrieve all Payload Delivery Tickets
    @GetMapping
    public ResponseEntity<List<PayloadDeliveryTicket>> getAllPDTs() {
        log.info("Retrieving all Payload Delivery Tickets.");
        List<PayloadDeliveryTicket> pdtList = payloadDeliveryRecordRepositoryPort.findAll();
        log.info("Retrieved {} Payload Delivery Tickets.", pdtList.size());
        return ResponseEntity.ok(pdtList);
    }

    // Retrieve Payload Delivery Ticket by license plate
    @GetMapping("/{licensePlate}")
    public ResponseEntity<?> getPDT(@PathVariable String licensePlate) {
        log.info("Retrieving PDT for license plate: {}", licensePlate);
        Optional<PayloadDeliveryTicket> pdt = payloadDeliveryRecordRepositoryPort.findByLicensePlate(licensePlate);

        if (pdt.isPresent()) {
            log.info("Found PDT for license plate: {}", licensePlate);
            return ResponseEntity.ok(pdt.get());
        } else {
            log.warn("No PDT found for license plate: {}", licensePlate);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("PDT not found for license plate: " + licensePlate);
        }
    }
}
