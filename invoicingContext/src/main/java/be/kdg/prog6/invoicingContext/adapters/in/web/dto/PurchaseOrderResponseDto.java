package be.kdg.prog6.invoicingContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PurchaseOrderResponseDto {
    private UUID purchaseOrderId;
    private String purchaseOrderNumber;
    private String customerNumber;
    private String customerName;
    private UUID sellerId;
    private String sellerName;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime orderDate;
    private String status;
    private double totalValue;
    private List<PurchaseOrderLineResponseDto> orderLines;

    @Data
    public static class PurchaseOrderLineResponseDto {
        private int lineNumber;
        private String rawMaterialName;
        private double amountInTons;
        private double pricePerTon;
        private double lineTotal;
    }
} 