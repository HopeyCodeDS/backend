package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.commands.RecognizeTruckCommand;
 
public interface RecognizeTruckUseCase {
    boolean recognizeTruck(RecognizeTruckCommand command);
} 