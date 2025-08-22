package be.kdg.prog6.warehousingContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WarehouseActivityWindow {
    private final List<WarehouseActivity> activities;
    
    public WarehouseActivityWindow() {
        this.activities = new ArrayList<>();
    }
    
    public void addActivity(WarehouseActivity activity) {
        activities.add(activity);
    }
    
    public double getCurrentCapacity() {
        return activities.stream()
            .mapToDouble(activity -> {
                switch (activity.getAction()) {
                    case PAYLOAD_DELIVERED: return activity.getAmount();
                    case LOADING_VESSEL: return -activity.getAmount();
                    default: return 0.0;
                }
            })
            .sum();
    }
    
    public double getUtilizationPercentage(double maxCapacity) {
        if (maxCapacity <= 0) return 0.0;
        return (getCurrentCapacity() / maxCapacity) * 100;
    }
    
    public List<WarehouseActivity> getActivitiesInTimeWindow(LocalDateTime from, LocalDateTime to) {
        return activities.stream()
            .filter(activity -> !activity.getPointInTime().isBefore(from) && 
                               !activity.getPointInTime().isAfter(to))
            .collect(Collectors.toList());
    }
    
    public List<WarehouseActivity> getRecentActivities(int limit) {
        return activities.stream()
            .sorted((a1, a2) -> a2.getPointInTime().compareTo(a1.getPointInTime()))
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    public boolean hasAvailableCapacity(double requiredAmount, double maxCapacity) {
        return (getCurrentCapacity() + requiredAmount) <= maxCapacity;
    }
} 