package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponseDto {
    private String id;
    private String number;
    private UUID sellerId;
    private String sellerName;
    private String material;
    private double currentStock;
    private double maxCapacity;
    private List<PayloadDto> payloads;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayloadDto {
        private UUID pdtId;
        private LocalDateTime deliveryTime;
        private double payloadWeight;
        private String rawMaterialName;  
        private UUID sellerId;
    }
}
