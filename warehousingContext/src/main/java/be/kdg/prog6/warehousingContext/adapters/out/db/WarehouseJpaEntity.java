package be.kdg.prog6.warehousingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(catalog = "warehousing", name = "warehouses")
public class WarehouseJpaEntity {
    
    @Id
    @Column(name = "warehouse_id")
    private UUID warehouseId;
    
    @Column(name = "warehouse_number", nullable = false, unique = true)
    private String warehouseNumber;
    
    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    
    @Column(name = "raw_material_name", nullable = false)
    private String rawMaterialName;
    
    @Column(name = "raw_material_price_per_ton", nullable = false)
    private Double rawMaterialPricePerTon;
    
    @Column(name = "raw_material_storage_price_per_ton_per_day", nullable = false)
    private Double rawMaterialStoragePricePerTonPerDay;
    
    @Column(name = "current_capacity", nullable = false)
    private Double currentCapacity;
    
    @Column(name = "max_capacity", nullable = false)
    private Double maxCapacity;
}
