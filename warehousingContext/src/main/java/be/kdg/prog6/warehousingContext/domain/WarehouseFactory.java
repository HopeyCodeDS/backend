package be.kdg.prog6.warehousingContext.domain;

import be.kdg.prog6.warehousingContext.domain.events.WarehouseCreatedEvent;
import be.kdg.prog6.warehousingContext.domain.events.PayloadDeliveredEvent;
import be.kdg.prog6.warehousingContext.domain.events.VesselLoadedEvent;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class WarehouseFactory {
    
    public Warehouse reconstructFromEvents(List<Object> events) {
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Cannot reconstruct warehouse from empty event list");
        }
        
        // First event must be creation event
        WarehouseCreatedEvent creationEvent = (WarehouseCreatedEvent) events.get(0);
        
        // Create warehouse with basic info
        Warehouse warehouse = new Warehouse(
            creationEvent.warehouseId(),
            creationEvent.warehouseNumber(),
            creationEvent.sellerId(),
            RawMaterial.fromName(creationEvent.assignedMaterial()),
            0.0,
            0L
        );
        
        // Apply all subsequent events
        for (int i = 1; i < events.size(); i++) {
            applyEvent(warehouse, events.get(i));
        }
        
        return warehouse;
    }
    
    public Warehouse reconstructFromSnapshot(WarehouseSnapshot snapshot, List<Object> eventsAfterSnapshot) {
        // Create warehouse from snapshot
        Warehouse warehouse = new Warehouse(
            snapshot.warehouseId(),
            snapshot.warehouseNumber(),
            snapshot.sellerId(),
            RawMaterial.fromName(snapshot.assignedMaterial()),
            snapshot.currentCapacity(),
            snapshot.version()
        );
        
        // Apply events that occurred after the snapshot
        for (Object event : eventsAfterSnapshot) {
            applyEvent(warehouse, event);
        }
        
        return warehouse;
    }
    
    private void applyEvent(Warehouse warehouse, Object event) {
        if (event instanceof PayloadDeliveredEvent) {
            PayloadDeliveredEvent payloadEvent = (PayloadDeliveredEvent) event;
            warehouse.deliverPayload(payloadEvent.amount(), payloadEvent.materialType(), payloadEvent.licensePlate());
        } else if (event instanceof VesselLoadedEvent) {
            VesselLoadedEvent loadingEvent = (VesselLoadedEvent) event;
            warehouse.loadVessel(loadingEvent.amount(), loadingEvent.materialType());
        }
        
    }
}


