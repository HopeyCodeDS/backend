package be.kdg.prog6.warehousingContext.adapters.out.db.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(catalog = "warehouse", name = "warehouses")
public class WarehouseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String warehouseId;
    private String customerId;
    private String materialType;
    private double capacity; // Max capacity in kt
    private double currentLoad; // Current load in kt

    public WarehouseJpaEntity(String warehouseId, String customerId, String materialType, double capacity) {
        this.warehouseId = warehouseId;
        this.customerId = customerId;
        this.materialType = materialType;
        this.capacity = capacity;
        this.currentLoad = 0;
    }

    public WarehouseJpaEntity() {
    }
}
