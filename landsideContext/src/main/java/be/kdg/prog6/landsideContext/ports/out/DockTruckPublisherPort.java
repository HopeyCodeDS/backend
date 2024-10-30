
package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.common.commands.DockingCommand;

@FunctionalInterface
public interface DockTruckPublisherPort {
    void publishDockingEvent(DockingCommand command);
}