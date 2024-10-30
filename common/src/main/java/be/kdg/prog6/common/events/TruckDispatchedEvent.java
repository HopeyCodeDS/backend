package be.kdg.prog6.common.events;

import be.kdg.prog6.common.domain.MaterialType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class TruckDispatchedEvent {
    private String licensePlate;
    private MaterialType materialType; // Change to MaterialType
    private String weighingBridgeNumber;
    private double weight;
    private String warehouseId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    private LocalDateTime timestamp;

    // No-arg constructor
    public TruckDispatchedEvent() {}

    @JsonCreator
    public TruckDispatchedEvent(
            @JsonProperty("licensePlate") String licensePlate,
            @JsonProperty("materialType") MaterialType materialType, // Change to MaterialType
            @JsonProperty("weighingBridgeNumber") String weighingBridgeNumber,
            @JsonProperty("weight") double weight,
            @JsonProperty("timestamp") LocalDateTime timestamp,
            @JsonProperty("warehouseId") String warehouseId) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.weighingBridgeNumber = weighingBridgeNumber;
        this.weight = weight;
        this.timestamp = timestamp;
        this.warehouseId = warehouseId;
    }

    // Getters and Setters
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public MaterialType getMaterialType() { return materialType; }
    public void setMaterialType(MaterialType materialType) { this.materialType = materialType; }

    public String getWeighingBridgeNumber() { return weighingBridgeNumber; }
    public void setWeighingBridgeNumber(String weighingBridgeNumber) { this.weighingBridgeNumber = weighingBridgeNumber; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "TruckDispatchedEvent{" +
                "licensePlate='" + licensePlate + '\'' +
                ", materialType=" + materialType +
                ", weighingBridgeNumber='" + weighingBridgeNumber + '\'' +
                ", weight=" + weight +
                ", warehouseId='" + warehouseId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
