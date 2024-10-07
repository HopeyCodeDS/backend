package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;

public interface GeneratePDTUseCase {
    PayloadDeliveryTicket generatePDT(String licensePlate, String conveyorBeltNumber, String newWeighingBridgeNumber);
}
