package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.ports.in.GetWarehousesBySellerUseCase;
import be.kdg.prog6.warehousingContext.ports.out.WarehouseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetWarehousesBySellerUseCaseImpl implements GetWarehousesBySellerUseCase {
    
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    
    @Override
    public List<Warehouse> getWarehousesBySeller(UUID sellerId) {
        return warehouseRepositoryPort.findAll().stream()
            .filter(warehouse -> warehouse.getSellerId().equals(sellerId))
            .collect(Collectors.toList());
    }
}
