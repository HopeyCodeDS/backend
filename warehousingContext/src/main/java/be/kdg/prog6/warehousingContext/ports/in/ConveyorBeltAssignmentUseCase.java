package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.warehousingContext.domain.ConveyorBelt;

@FunctionalInterface
public interface ConveyorBeltAssignmentUseCase {
    ConveyorBelt assignConveyorBelt(MaterialType materialType);
}
