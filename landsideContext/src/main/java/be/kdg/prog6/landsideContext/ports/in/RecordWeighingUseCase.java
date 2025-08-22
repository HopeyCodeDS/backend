package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.commands.RecordWeighingCommand;

public interface RecordWeighingUseCase {
    void recordWeighing(RecordWeighingCommand command);
} 