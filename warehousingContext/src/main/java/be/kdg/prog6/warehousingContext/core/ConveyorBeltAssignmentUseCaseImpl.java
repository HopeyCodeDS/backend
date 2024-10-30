package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.warehousingContext.domain.ConveyorBelt;
import be.kdg.prog6.warehousingContext.ports.in.ConveyorBeltAssignmentUseCase;
import org.springframework.stereotype.Service;

@Service
public class ConveyorBeltAssignmentUseCaseImpl implements ConveyorBeltAssignmentUseCase {
    @Override
    public ConveyorBelt assignConveyorBelt(MaterialType materialType) {
        switch (materialType) {
            case GYPSUM:
                return new ConveyorBelt("Conveyor-1", materialType);
            case IRON_ORE:
                return new ConveyorBelt("Conveyor-2", materialType);
            case CEMENT:
                return new ConveyorBelt("Conveyor-3", materialType);
            case PETCOKE:
                return new ConveyorBelt("Conveyor-4", materialType);
            case SLAG:
                return new ConveyorBelt("Conveyor-5", materialType);
            default:
                return new ConveyorBelt("Conveyor-General", materialType);
        }
    }
}
