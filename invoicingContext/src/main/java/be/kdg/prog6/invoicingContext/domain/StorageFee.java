package be.kdg.prog6.invoicingContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class StorageFee {
    private final UUID storageFeeId;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDate calculationDate;
    private final String warehouseNumber;
    private final String materialType;
    private final UUID sellerId;
    private final double totalDailyFee;
    private final int totalDeliveryBatches;
    
    public StorageFee(LocalDate calculationDate, String warehouseNumber, String materialType, UUID sellerId,
                     double totalDailyFee, int totalDeliveryBatches) {
        this.storageFeeId = UUID.randomUUID();
        this.calculationDate = calculationDate;
        this.warehouseNumber = warehouseNumber;
        this.materialType = materialType;
        this.sellerId = sellerId;
        this.totalDailyFee = totalDailyFee;
        this.totalDeliveryBatches = totalDeliveryBatches;
    }

    public StorageFee(LocalDate calculationDate, String warehouseNumber, String materialType, String sellerId,
                      double totalDailyFee, int totalDeliveryBatches) {
        this.storageFeeId = UUID.randomUUID();
        this.calculationDate = calculationDate;
        this.warehouseNumber = warehouseNumber;
        this.materialType = materialType;
        this.sellerId = UUID.fromString(sellerId);
        this.totalDailyFee = totalDailyFee;
        this.totalDeliveryBatches = totalDeliveryBatches;
    }
} 