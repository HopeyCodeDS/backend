package be.kdg.prog6.landsideContext.facade;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GenerateTicketCommand {
    // Getters
    private String licensePlate;
    private double tareWeight;


    @JsonCreator
    public GenerateTicketCommand(
            @JsonProperty("licensePlate") String licensePlate,
            @JsonProperty("tareWeight") double tareWeight) {
        this.licensePlate = licensePlate;
        this.tareWeight = tareWeight;
    }


    @Override
    public String toString() {
        return "GenerateTicketCommand{" +
                "licensePlate='" + licensePlate + '\'' +
                ", tareWeight=" + tareWeight +
                '}';
    }
}
