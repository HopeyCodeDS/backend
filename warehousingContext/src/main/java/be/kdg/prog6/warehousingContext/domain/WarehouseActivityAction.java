package be.kdg.prog6.warehousingContext.domain;

public enum WarehouseActivityAction {
    PAYLOAD_DELIVERED("Payload Delivered"),
    LOADING_VESSEL("Loading Vessel");
    
    private final String displayName;
    
    WarehouseActivityAction(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
} 