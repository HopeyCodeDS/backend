package be.kdg.prog6.invoicingContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubmitPurchaseOrderRequestDto {
    private String purchaseOrderNumber;
    private String customerNumber;
    private String customerName;
    private String sellerId;
    private String sellerName;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime orderDate;
    private List<PurchaseOrderLineDto> orderLines;

    @Data
    public static class PurchaseOrderLineDto {
        private int lineNumber;
        private String rawMaterialName;
        private double amountInTons;
        private double pricePerTon;
    }
} 