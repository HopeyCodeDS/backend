package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.warehousingContext.ports.in.ConveyorBeltAssignmentUseCase;

public class ConveyorBeltAssignmentUseCaseImpl implements ConveyorBeltAssignmentUseCase {
    @Override
    public String assignConveyorBelt(MaterialType materialType) {
        switch (materialType) {
            case GYPSUM:
                return "Conveyor-1";
            case IRON_ORE:
                return "Conveyor-2";
            case CEMENT:
                return "Conveyor-3";
            case PETCOKE:
                return "Conveyor-4";
            case SLAG:
                return "Conveyor-5";
            default:
                return "Conveyor-General";
        }
    }
}
