package be.kdg.prog6.warehousingContext.adapters.in.web;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.DeliverPayloadRequestDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.PayloadDeliveryMapper;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.domain.commands.DeliverPayloadCommand;
import be.kdg.prog6.warehousingContext.ports.in.DeliverPayloadUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/warehousing/payload-delivery")
@RequiredArgsConstructor
@Slf4j
public class PayloadDeliveryController {
    
    private final DeliverPayloadUseCase deliverPayloadUseCase;
    private final PayloadDeliveryMapper payloadDeliveryMapper;
    private final PDTRepositoryPort pdtRepositoryPort;

    @PostMapping("/deliver")
    public ResponseEntity<Map<String, Object>> deliverPayload(@RequestBody DeliverPayloadRequestDto requestDto) {
        try {
            log.info("Received payload delivery request for truck: {}", requestDto.getLicensePlate());
            
            DeliverPayloadCommand command = payloadDeliveryMapper.toCommand(requestDto);
            log.info("Mapped to command: {}", command);
            
            PayloadDeliveryTicket pdt = deliverPayloadUseCase.deliverPayload(command);

            log.info("Payload delivered successfully. PDT generated: {}", pdt.getPdtId());

            return ResponseEntity.ok(Map.of(
                "pdtId", pdt.getPdtId(),
                "licensePlate", pdt.getLicensePlate(),
                "rawMaterialName", pdt.getRawMaterialName(),
                "warehouseNumber", pdt.getWarehouseNumber(),
                "conveyorBeltNumber", pdt.getConveyorBeltNumber(),
                "payloadWeight", pdt.getPayloadWeight(),
                "deliveryTime", pdt.getDeliveryTime(),
                "newWeighingBridgeNumber", pdt.getNewWeighingBridgeNumber(),
                "message", "Payload delivered successfully. PDT generated with new weighing bridge number: " + pdt.getNewWeighingBridgeNumber()
            ));
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Validation error",
                "message", e.getMessage()
            ));
        } catch (IllegalStateException e) {
            log.error("Business rule violation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Business rule violation",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            log.error("Unexpected error during payload delivery: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Internal server error",
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/pdt")
    public ResponseEntity<List<PayloadDeliveryTicket>> getAllPDTs() {
        List<PayloadDeliveryTicket> pdts = pdtRepositoryPort.findAll();
        return ResponseEntity.ok(pdts);
    }
    
    @GetMapping("/pdt/{licensePlate}")
    public ResponseEntity<Map<String, Object>> getPDTByLicensePlate(@PathVariable String licensePlate) {
        Optional<PayloadDeliveryTicket> pdt = pdtRepositoryPort.findByLicensePlate(licensePlate);
        
        if (pdt.isPresent()) {
            PayloadDeliveryTicket ticket = pdt.get();
            return ResponseEntity.ok(Map.of(
                "pdtId", ticket.getPdtId(),
                "licensePlate", ticket.getLicensePlate(),
                "rawMaterialName", ticket.getRawMaterialName(),
                "warehouseNumber", ticket.getWarehouseNumber(),
                "conveyorBeltNumber", ticket.getConveyorBeltNumber(),
                "payloadWeight", ticket.getPayloadWeight(),
                "sellerId", ticket.getSellerId(),
                "deliveryTime", ticket.getDeliveryTime(),
                "newWeighingBridgeNumber", ticket.getNewWeighingBridgeNumber()
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 