package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.common.domain.MaterialType;

public interface ConveyorBeltAssignmentUseCase {
    String assignConveyorBelt(MaterialType materialType);
}
