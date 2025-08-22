package be.kdg.prog6.warehousingContext.domain.commands;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrackPurchaseOrderFulfillmentCommand {
    private final String purchaseOrderNumber;
    private final String customerNumber;
    private final String customerName;
    private final double totalValue;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime orderDate;
    private final List<OrderLine> orderLines;

    @Data
    public static class OrderLine {
        private final String rawMaterialName;
        private final double amountInTons;
        private final double pricePerTon;
    }
} 