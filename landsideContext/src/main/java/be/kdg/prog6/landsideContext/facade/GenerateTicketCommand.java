package be.kdg.prog6.landsideContext.facade;


import lombok.Getter;

@Getter
public class GenerateTicketCommand {
    // Getters
    private final String licensePlate;
    private final double grossWeight;
    private final double tareWeight;


    public GenerateTicketCommand(String licensePlate, double grossWeight, double tareWeight) {
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
    }


    @Override
    public String toString() {
        return "GenerateTicketCommand{" +
                "licensePlate='" + licensePlate + '\'' +
                ", grossWeight=" + grossWeight +
                ", tareWeight=" + tareWeight +
                '}';
    }
}
