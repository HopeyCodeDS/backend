package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.domain.WarehouseFactory;
import be.kdg.prog6.warehousingContext.domain.WarehouseSnapshot;
import be.kdg.prog6.warehousingContext.domain.events.WarehouseCreatedEvent;
import be.kdg.prog6.warehousingContext.domain.events.PayloadDeliveredEvent;
import be.kdg.prog6.warehousingContext.domain.events.VesselLoadedEvent;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehouseDatabaseAdapter implements WarehouseRepositoryPort {
    
    private final WarehouseJpaRepository warehouseJpaRepository;
    private final WarehouseMapper warehouseMapper;
    private final EventStoreRepository eventStoreRepository;
    private final SnapshotRepository snapshotRepository;
    private final WarehouseFactory warehouseFactory;
    private final ObjectMapper objectMapper;
    
    @Override
    public List<Warehouse> findAvailableWarehouses(UUID sellerId, String rawMaterialName) {
        List<WarehouseJpaEntity> jpaEntities = warehouseJpaRepository.findBySellerIdAndRawMaterialName(sellerId, rawMaterialName);
        return jpaEntities.stream()
                .map(warehouseMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Warehouse> findById(UUID warehouseId) {
        // Get from current state
        Optional<WarehouseJpaEntity> entity = warehouseJpaRepository.findById(warehouseId);
        
        if (entity.isPresent()) {
            // Convert to domain object
            return entity.map(warehouseMapper::toDomain);
        }
        
        // If not found, reconstruct from events
        log.info("Warehouse {} not found in current state, attempting event sourcing reconstruction", warehouseId);
        return reconstructFromEvents(warehouseId);
    }
    
    @Override
    public Optional<Warehouse> findByWarehouseNumber(String warehouseNumber) {
        // Get from current state
        Optional<WarehouseJpaEntity> entity = warehouseJpaRepository.findByWarehouseNumber(warehouseNumber);
        
        if (entity.isPresent()) {
            // Convert to domain object
            return entity.map(warehouseMapper::toDomain);
        }
        
        // If not found, reconstruct from events
        log.info("Warehouse {} not found in current state, attempting event sourcing reconstruction", warehouseNumber);
        return reconstructFromEventsByWarehouseNumber(warehouseNumber);
    }
    
    @Override
    public void save(Warehouse warehouse) {
        // Save current state
        WarehouseJpaEntity jpaEntity = warehouseMapper.toJpaEntity(warehouse);
        warehouseJpaRepository.save(jpaEntity);
        
        // Store events for event sourcing
        storeWarehouseEvents(warehouse);
    }
    
    @Override
    public List<Warehouse> findAll() {
        return warehouseJpaRepository.findAll().stream()
                .map(warehouseMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public double getTotalRawMaterialInWarehouses() {
        return warehouseJpaRepository.findAll().stream()
                .mapToDouble(entity -> entity.getCurrentCapacity())
                .sum();
    }
    
    @Override
    public void deleteById(UUID warehouseId) {
        warehouseJpaRepository.deleteById(warehouseId);
    }
    
    // ========== EVENT SOURCING METHODS ========== //
    
    private Optional<Warehouse> reconstructFromEvents(UUID warehouseId) {
        // Get events from event store
        List<EventStoreEntity> allEvents = eventStoreRepository.findByAggregateIdOrderByVersion(warehouseId);
        
        // Filter out duplicate WarehouseCreatedEvents, but keep the first one
        List<EventStoreEntity> filteredEvents = new ArrayList<>();
        boolean creationEventFound = false;

        for (EventStoreEntity event : allEvents) {
            if (event.getEventType().equals("WarehouseCreatedEvent")) {
                if (!creationEventFound) {
                    filteredEvents.add(event);
                    creationEventFound = true;
                }
                // Skip duplicate creation events
            } else {
                filteredEvents.add(event);
            }
        }
        
        List<Object> domainEvents = filteredEvents.stream()
            .map(this::convertToDomainEvent)
            .collect(Collectors.toList());

        try {
            // Reconstruct warehouse with filtered events
            Warehouse warehouse = warehouseFactory.reconstructFromEvents(domainEvents);
            log.info("Successfully reconstructed warehouse {} from events", warehouseId);
            return Optional.of(warehouse);
        } catch (Exception e) {
            log.error("Failed to reconstruct warehouse {} from events", warehouseId, e);
            return Optional.empty();
        }
    }
    
    private Optional<Warehouse> reconstructFromEventsByWarehouseNumber(String warehouseNumber) {
        // Get events from event store by warehouse number
        List<EventStoreEntity> allEvents = eventStoreRepository.findAll();
        List<EventStoreEntity> relevantEvents = allEvents.stream()
            .filter(event -> {
                try {
                    Map<String, String> eventData = objectMapper.readValue(event.getEventData(), new TypeReference<Map<String, String>>() {});
                    return warehouseNumber.equals(eventData.get("warehouseNumber"));
                } catch (Exception e) {
                    return false;
                }
            })
            .collect(Collectors.toList());
        
        if (relevantEvents.isEmpty()) {
            log.warn("No events found for warehouse number {}", warehouseNumber);
            return Optional.empty();
        }
        
        log.info("Reconstructing warehouse {} from {} events", warehouseNumber, relevantEvents.size());
        
        // Convert events to domain events
        List<Object> domainEvents = relevantEvents.stream()
            .map(this::convertToDomainEvent)
            .collect(Collectors.toList());
        
        try {
            // Reconstruct warehouse using factory
            Warehouse warehouse = warehouseFactory.reconstructFromEvents(domainEvents);
            log.info("Successfully reconstructed warehouse {} from events", warehouseNumber);
            return Optional.of(warehouse);
        } catch (Exception e) {
            log.error("Failed to reconstruct warehouse {} from events", warehouseNumber, e);
            return Optional.empty();
        }
    }
    
    private void storeWarehouseEvents(Warehouse warehouse) {
        // Get domain events from warehouse
        List<Object> domainEvents = warehouse.getDomainEvents();
        
        if (domainEvents.isEmpty()) {
            return;
        }
        
        // Store each event
        for (Object event : domainEvents) {
            EventStoreEntity eventEntity = new EventStoreEntity();
            eventEntity.setEventId(UUID.randomUUID());
            eventEntity.setAggregateId(warehouse.getWarehouseId());
            eventEntity.setEventType(event.getClass().getSimpleName());
            eventEntity.setVersion(getNextVersion(warehouse.getWarehouseId()));
            eventEntity.setEventData(convertEventToJson(event));
            eventEntity.setTimestamp(LocalDateTime.now());
            
            eventStoreRepository.save(eventEntity);
        }
        
        // Clear domain events after storing
        warehouse.clearDomainEvents();
        
        // Check if I need to create a snapshot (every 2 events - 
        // i did this because I wanted to quickly test my snapshot functionality)
        if (shouldCreateSnapshot(warehouse.getWarehouseId())) {
            createSnapshot(warehouse);
        }
    }
    
    private long getNextVersion(UUID warehouseId) {
        List<EventStoreEntity> events = eventStoreRepository.findByAggregateIdOrderByVersion(warehouseId);
        return events.isEmpty() ? 1 : events.get(events.size() - 1).getVersion() + 1;
    }
    
    private boolean shouldCreateSnapshot(UUID warehouseId) {
        List<EventStoreEntity> events = eventStoreRepository.findByAggregateIdOrderByVersion(warehouseId);
        
        // Strategy 1: Snapshot after every 2 of either event type
        boolean eventBasedSnapshot = shouldCreateEventBasedSnapshot(warehouseId, events);
        
        // Strategy 2: Business rule - Snapshot when warehouse reaches 80% capacity
        boolean capacityBasedSnapshot = shouldCreateCapacityBasedSnapshot(warehouseId, events);
        
        // Create snapshot if either condition is met
        boolean shouldCreate = eventBasedSnapshot || capacityBasedSnapshot;
        
        if (shouldCreate) {
            log.info("Snapshot decision for warehouse {}: event-based={}, capacity-based={}", 
                warehouseId, eventBasedSnapshot, capacityBasedSnapshot);
        }
        
        return shouldCreate;
    }
    
    private boolean shouldCreateEventBasedSnapshot(UUID warehouseId, List<EventStoreEntity> events) {
        // Count specific event types (either PayloadDeliveredEvent OR VesselLoadedEvent)
        long payloadDeliveredCount = events.stream()
            .filter(event -> "PayloadDeliveredEvent".equals(event.getEventType()))
            .count();
            
        long vesselLoadedCount = events.stream()
            .filter(event -> "VesselLoadedEvent".equals(event.getEventType()))
            .count();
        
        // Snapshot after every 2 of either event type
        boolean shouldCreatePayload = payloadDeliveredCount > 0 && payloadDeliveredCount % 2 == 0;
        boolean shouldCreateVessel = vesselLoadedCount > 0 && vesselLoadedCount % 2 == 0;
        
        boolean shouldCreate = shouldCreatePayload || shouldCreateVessel;
        
        if (shouldCreate) {
            log.info("Event-based snapshot for warehouse {}: {} payload deliveries, {} vessel loadings", 
                warehouseId, payloadDeliveredCount, vesselLoadedCount);
        }
        
        return shouldCreate;
    }

    private boolean shouldCreateCapacityBasedSnapshot(UUID warehouseId, List<EventStoreEntity> events) {
        // Business rule: Snapshot when warehouse reaches 80% capacity
        Optional<EventStoreEntity> lastPayloadEvent = events.stream()
            .filter(event -> "PayloadDeliveredEvent".equals(event.getEventType()))
            .max((e1, e2) -> Long.compare(e1.getVersion(), e2.getVersion()));
        
        if (lastPayloadEvent.isPresent()) {
            try {
                Map<String, String> eventData = objectMapper.readValue(
                    lastPayloadEvent.get().getEventData(), 
                    new TypeReference<Map<String, String>>() {}
                );
                
                double newCapacity = Double.parseDouble(eventData.get("newCapacity"));
                double maxCapacity = 500_000.0;
                double utilizationPercentage = (newCapacity / maxCapacity) * 100;
                
                if (utilizationPercentage >= 80.0) {
                    log.info("Capacity-based snapshot for warehouse {}: {}% capacity ({} tons / {} tons)", 
                        warehouseId, utilizationPercentage, newCapacity, maxCapacity);
                    return true;
                }
            } catch (Exception e) {
                log.warn("Failed to parse event data for capacity-based snapshot decision", e);
            }
        }
        
        return false;
    }

    private void createSnapshot(Warehouse warehouse) {
        try {
            // Get the actual event count for versioning
            List<EventStoreEntity> events = eventStoreRepository.findByAggregateIdOrderByVersion(warehouse.getWarehouseId());
            long actualVersion = events.size(); // Use event count as version
            
            // Create warehouse snapshot
            WarehouseSnapshot snapshot = new WarehouseSnapshot(warehouse);
            
            // Create snapshot entity
            SnapshotEntity snapshotEntity = new SnapshotEntity();
            snapshotEntity.setSnapshotId(UUID.randomUUID());
            snapshotEntity.setAggregateId(warehouse.getWarehouseId());
            snapshotEntity.setVersion(actualVersion);
            snapshotEntity.setSnapshotData(convertSnapshotToJson(snapshot));
            snapshotEntity.setTimestamp(LocalDateTime.now());
            
            // Save snapshot to database
            snapshotRepository.save(snapshotEntity);
            
            log.info("Created snapshot for warehouse {} at version {} (after {} events)", 
                warehouse.getWarehouseId(), actualVersion, actualVersion);
                
        } catch (Exception e) {
            log.error("Failed to create snapshot for warehouse {}", warehouse.getWarehouseId(), e);
        }
    }
    
    private Object convertToDomainEvent(EventStoreEntity eventEntity) {
        try {
            Map<String, String> eventData = objectMapper.readValue(eventEntity.getEventData(), new TypeReference<Map<String, String>>() {});
            
            // Convert stored event data back to domain event
            switch (eventEntity.getEventType()) {
                case "WarehouseCreatedEvent":
                    return new WarehouseCreatedEvent(
                        eventEntity.getAggregateId(), 
                        eventData.get("warehouseNumber"),
                        UUID.fromString(eventData.get("sellerId")),
                        eventData.get("assignedMaterial"),
                        Double.parseDouble(eventData.get("maxCapacity"))
                    );
                case "PayloadDeliveredEvent":
                    return new PayloadDeliveredEvent(
                        eventEntity.getAggregateId(), 
                        Double.parseDouble(eventData.get("amount")),
                        eventData.get("materialType"),
                        eventData.get("licensePlate"),
                        Double.parseDouble(eventData.get("newCapacity"))
                    );
                case "VesselLoadedEvent":
                    return new VesselLoadedEvent(
                        eventEntity.getAggregateId(), 
                        Double.parseDouble(eventData.get("amount")),
                        eventData.get("materialType"),
                        Double.parseDouble(eventData.get("newCapacity"))
                    );
                default:
                    throw new IllegalArgumentException("Unknown event type: " + eventEntity.getEventType());
            }
        } catch (Exception e) {
            log.error("Failed to convert event to domain event", e);
            throw new RuntimeException("Event conversion failed", e);
        }
    }
    
    private String convertEventToJson(Object event) {
        try {
            Map<String, String> data = new HashMap<>();
            
            if (event instanceof WarehouseCreatedEvent) {
                WarehouseCreatedEvent e = (WarehouseCreatedEvent) event;
                data.put("warehouseNumber", e.warehouseNumber());
                data.put("sellerId", e.sellerId().toString());
                data.put("assignedMaterial", e.assignedMaterial());
                data.put("maxCapacity", String.valueOf(e.maxCapacity()));
            } else if (event instanceof PayloadDeliveredEvent) {
                PayloadDeliveredEvent e = (PayloadDeliveredEvent) event;
                data.put("amount", String.valueOf(e.amount()));
                data.put("materialType", e.materialType());
                data.put("licensePlate", e.licensePlate());
                data.put("newCapacity", String.valueOf(e.newCapacity()));
            } else if (event instanceof VesselLoadedEvent) {
                VesselLoadedEvent e = (VesselLoadedEvent) event;
                data.put("amount", String.valueOf(e.amount()));
                data.put("materialType", e.materialType());
            }
            
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error("Failed to convert event to JSON", e);
            throw new RuntimeException("Event JSON conversion failed", e);
        }
    }
    
    private String convertSnapshotToJson(WarehouseSnapshot snapshot) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("warehouseNumber", snapshot.warehouseNumber());
            data.put("sellerId", snapshot.sellerId().toString());
            data.put("assignedMaterial", snapshot.assignedMaterial());
            data.put("maxCapacity", String.valueOf(snapshot.maxCapacity()));
            data.put("currentCapacity", String.valueOf(snapshot.currentCapacity()));
            data.put("version", String.valueOf(snapshot.version()));
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error("Failed to convert snapshot to JSON", e);
            throw new RuntimeException("Snapshot JSON conversion failed", e);
        }
    }
}