package be.kdg.prog6.warehousingContext.domain;

import be.kdg.prog6.common.domain.MaterialType;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Table(catalog = "warehouse", name = "payload_delivery_tickets")
public class PayloadDeliveryTicket {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String licensePlate;

    @Getter
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;
    @Getter
    private String conveyorBeltNumber;
    @Getter
    private String weighingBridgeNumber;
    @Getter
    private LocalDateTime deliveryTime;
    private double weight;

    protected PayloadDeliveryTicket() {
        // Default constructor for JPA
    }
    public PayloadDeliveryTicket(String licensePlate, MaterialType materialType, String conveyorBeltNumber, String weighingBridgeNumber, double weight) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.conveyorBeltNumber = conveyorBeltNumber;
        this.weighingBridgeNumber = weighingBridgeNumber;
        this.deliveryTime = LocalDateTime.now();
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "PayloadDeliveryTicket{" +
                "licensePlate='" + licensePlate + '\'' +
                ", materialType=" + materialType +
                ", conveyorBeltNumber='" + conveyorBeltNumber + '\'' +
                ", weighingBridgeNumber='" + weighingBridgeNumber + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", weight=" + weight +
                '}';
    }
}
