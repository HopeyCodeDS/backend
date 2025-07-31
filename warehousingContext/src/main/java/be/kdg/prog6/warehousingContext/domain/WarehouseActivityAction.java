package be.kdg.prog6.warehousingContext.domain;

public enum WarehouseActivityAction {
    MATERIAL_DELIVERED("Material Delivered"),
    MATERIAL_SHIPPED("Material Shipped"),
    CAPACITY_ADJUSTMENT("Capacity Adjustment");
    
    private final String displayName;
    
    WarehouseActivityAction(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
} 