package be.kdg.prog6.warehousingContext.domain;

import java.time.LocalDateTime;

public class PayloadDeliveryTicket {
    private String licensePlate;
    private String materialType;
    private String conveyorBeltNumber;
    private String newWeighingBridgeNumber;
    private LocalDateTime timestamp;

    public PayloadDeliveryTicket(String licensePlate, String materialType, String conveyorBeltNumber) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.conveyorBeltNumber = conveyorBeltNumber;
        this.newWeighingBridgeNumber = newWeighingBridgeNumber;
        this.timestamp = LocalDateTime.now();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getConveyorBeltNumber() {
        return conveyorBeltNumber;
    }

    public String getNewWeighingBridgeNumber() {
        return newWeighingBridgeNumber;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "PayloadDeliveryTicket{" +
                "licensePlate='" + licensePlate + '\'' +
                ", materialType='" + materialType + '\'' +
                ", conveyorBeltNumber='" + conveyorBeltNumber + '\'' +
                ", newWeighingBridgeNumber='" + newWeighingBridgeNumber + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
