package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PayloadReceivedEvent {
    private String licensePlate;
    private String conveyorBeltId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    private LocalDateTime deliveryTime;
    private String materialType;
    private String weighingBridgeNumber;
    private double weight;

    // Default no-arg constructor
    public PayloadReceivedEvent() {}

    // Constructor with all fields
    @JsonCreator
    public PayloadReceivedEvent(
            @JsonProperty("licensePlate") String licensePlate,
            @JsonProperty("conveyorBeltId") String conveyorBeltId,
            @JsonProperty("deliveryTime") LocalDateTime deliveryTime,
            @JsonProperty("materialType") String materialType,
            @JsonProperty("weighingBridgeNumber") String weighingBridgeNumber,
            @JsonProperty("weight") double weight
    ) {
        this.licensePlate = licensePlate;
        this.conveyorBeltId = conveyorBeltId;
        this.deliveryTime = deliveryTime;
        this.materialType = materialType;
        this.weighingBridgeNumber = weighingBridgeNumber;
        this.weight = weight;
    }

}
