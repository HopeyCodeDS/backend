package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeliverPayloadRequestDto {
    private String licensePlate;
    private String rawMaterialName;
    private String warehouseNumber;
    private String conveyorBeltNumber;
    private double payloadWeight;
    private String sellerId;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime deliveryTime;
    private String newWeighingBridgeNumber;
} 