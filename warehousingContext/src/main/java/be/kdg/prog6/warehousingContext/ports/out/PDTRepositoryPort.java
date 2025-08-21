package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import java.util.List;
import java.util.Optional;

public interface PDTRepositoryPort {
    PayloadDeliveryTicket save(PayloadDeliveryTicket pdt);
    Optional<PayloadDeliveryTicket> findByLicensePlate(String licensePlate);
    List<PayloadDeliveryTicket> findAll();
    List<PayloadDeliveryTicket> findByRawMaterialOrderByDeliveryTimeAsc(String rawMaterialName);
    List<PayloadDeliveryTicket> findByWarehouseNumber(String warehouseNumber);
} 