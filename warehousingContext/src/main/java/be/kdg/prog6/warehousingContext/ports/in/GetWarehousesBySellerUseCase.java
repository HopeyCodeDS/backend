package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import java.util.List;
import java.util.UUID;

public interface GetWarehousesBySellerUseCase {
    List<Warehouse> getWarehousesBySeller(UUID sellerId);
}
