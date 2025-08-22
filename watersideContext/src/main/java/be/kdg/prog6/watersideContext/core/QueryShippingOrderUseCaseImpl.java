package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.QueryShippingOrderUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryShippingOrderUseCaseImpl implements QueryShippingOrderUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public List<ShippingOrder> getAllShippingOrders() {
        log.info("Fetching all shipping orders");
        return shippingOrderRepositoryPort.findAll();
    }
    
    @Override
    public Optional<ShippingOrder> getShippingOrderById(UUID shippingOrderId) {
        log.info("Fetching shipping order by ID: {}", shippingOrderId);
        return shippingOrderRepositoryPort.findById(shippingOrderId);
    }
    
    @Override
    public List<ShippingOrder> getShippingOrdersByStatus(ShippingOrder.ShippingOrderStatus status) {
        log.info("Fetching shipping orders by status: {}", status);
        return shippingOrderRepositoryPort.findByStatus(status);
    }
    
    @Override
    public List<ShippingOrder> getShippingOrdersByVessel(String vesselNumber) {
        log.info("Fetching shipping orders by vessel: {}", vesselNumber);
        return shippingOrderRepositoryPort.findByVesselNumber(vesselNumber);
    }
    
    @Override
    public List<ShippingOrder> getArrivedShippingOrders() {
        log.info("Fetching arrived shipping orders");
        return shippingOrderRepositoryPort.findByStatus(ShippingOrder.ShippingOrderStatus.ARRIVED);
    }
    
    @Override
    public List<ShippingOrder> getLoadingShippingOrders() {
        log.info("Fetching loading shipping orders");
        return shippingOrderRepositoryPort.findByStatus(ShippingOrder.ShippingOrderStatus.READY_FOR_LOADING);
    }
    
    @Override
    public List<ShippingOrder> getDepartedShippingOrders() {
        log.info("Fetching departed shipping orders");
        return shippingOrderRepositoryPort.findByStatus(ShippingOrder.ShippingOrderStatus.DEPARTED);
    }
    
    @Override
    public List<ShippingOrder> getReadyForLoadingShippingOrders() {
        log.info("Fetching ready for loading shipping orders");
        return shippingOrderRepositoryPort.findByStatus(ShippingOrder.ShippingOrderStatus.READY_FOR_LOADING);
    }
}
