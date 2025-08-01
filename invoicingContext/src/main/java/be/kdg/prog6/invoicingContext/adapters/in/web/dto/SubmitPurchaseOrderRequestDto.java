package be.kdg.prog6.invoicingContext.adapters.in.web.dto;

import lombok.Data;
import java.util.List;

@Data
public class SubmitPurchaseOrderRequestDto {
    private String purchaseOrderNumber;
    private String customerNumber;
    private String customerName;
    private List<PurchaseOrderLineDto> orderLines;

    @Data
    public static class PurchaseOrderLineDto {
        private String rawMaterialName;
        private double amountInTons;
        private double pricePerTon;
    }
} 