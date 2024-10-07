
package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.common.events.DockingEvent;

public interface DockTruckPublisherPort {
    void publishDockingEvent(DockingEvent event);
}