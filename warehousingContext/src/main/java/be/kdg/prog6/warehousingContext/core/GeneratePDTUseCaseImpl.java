package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.in.GeneratePDTUseCase;

public class GeneratePDTUseCaseImpl implements GeneratePDTUseCase {
    @Override
    public PayloadDeliveryTicket generatePDT(String licensePlate, String conveyorBeltNumber, String newWeighingBridgeNumber) {
        return new PayloadDeliveryTicket(licensePlate, conveyorBeltNumber, newWeighingBridgeNumber);
    }
}
