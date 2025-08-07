package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.invoicingContext.domain.StorageTracking;
import java.util.List;
import java.util.Map;

public interface StorageTrackingRepositoryPort {
    void save(StorageTracking storageTracking);
    List<StorageTracking> findByWarehouseAndMaterialOrderByDeliveryTime(String warehouseNumber, String materialType);
    List<StorageTracking> findByCustomerNumber(String customerNumber);
    List<StorageTracking> findAll();
    Map<String, List<StorageTracking>> findAllGroupedByWarehouseAndMaterial();
} 