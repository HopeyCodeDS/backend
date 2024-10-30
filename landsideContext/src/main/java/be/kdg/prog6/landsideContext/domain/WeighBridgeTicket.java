package be.kdg.prog6.landsideContext.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class WeighBridgeTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key
    private String licensePlate;
    private double grossWeight;
    private double tareWeight;
    private double netWeight;
    private LocalDateTime timestamp;

    // Default constructor for JPA
    public WeighBridgeTicket() {}

    // Constructor
    public WeighBridgeTicket(String licensePlate, double grossWeight, double tareWeight) {
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = calculateNetWeight();
        this.timestamp = LocalDateTime.now();
    }


    public double calculateNetWeight() { return grossWeight - tareWeight; }

    @Override
    public String toString() {
        return "Weighbridge Ticket: \n" +
                "License Plate: " + licensePlate + "\n" +
                "Gross Weight: " + grossWeight + "\n" +
                "Tare Weight: " + tareWeight + "\n" +
                "Net Weight: " + netWeight + "\n" +
                "Timestamp: " + timestamp;
    }
}
