package be.kdg.prog6.warehousingContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PayloadDeliveryTicket {
    private final UUID pdtId;
    private final String licensePlate;
    private final String rawMaterialName;
    private final String warehouseNumber;
    private final String conveyorBeltNumber;
    private final double payloadWeight;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime deliveryTime;
    private final UUID sellerId;
    private final String newWeighingBridgeNumber;

    public PayloadDeliveryTicket(UUID pdtId, String licensePlate, String rawMaterialName, 
                                String warehouseNumber, String conveyorBeltNumber, 
                                double payloadWeight, UUID sellerId, LocalDateTime deliveryTime, String newWeighingBridgeNumber) {
        this.pdtId = pdtId;
        this.licensePlate = licensePlate;
        this.rawMaterialName = rawMaterialName;
        this.warehouseNumber = warehouseNumber;
        this.conveyorBeltNumber = conveyorBeltNumber;
        this.payloadWeight = payloadWeight;
        this.sellerId = sellerId;
        this.deliveryTime = deliveryTime;
        this.newWeighingBridgeNumber = newWeighingBridgeNumber;
    }
} 