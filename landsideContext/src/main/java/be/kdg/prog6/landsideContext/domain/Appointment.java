package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.domain.SellerID;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Appointment {
    private Truck truck;
    private LocalDateTime arrivalWindow;
    private SellerID sellerId;
    private MaterialType materialType;
    private Slot slot;

    public Appointment(Truck truck, LocalDateTime arrivalWindow, SellerID sellerId, MaterialType materialType, Slot slot) {
        this.truck = truck;
        this.arrivalWindow = arrivalWindow;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "truck=" + truck +
                ", arrivalWindow=" + arrivalWindow +
                ", sellerId=" + sellerId +
                ", materialType=" + materialType +
                ", slot=" + slot +
                '}';
    }
}
