package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import be.kdg.prog6.warehousingContext.ports.in.GetPurchaseOrderFulfillmentUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderFulfillmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetPurchaseOrderFulfillmentUseCaseImpl implements GetPurchaseOrderFulfillmentUseCase {

    private final PurchaseOrderFulfillmentRepositoryPort repositoryPort;

    @Override
    @Transactional
    public List<PurchaseOrderFulfillmentTracking> getFulfilledOrders() {
        log.info("Warehouse manager requesting fulfilled purchase orders");
        return repositoryPort.findByStatus(PurchaseOrderFulfillmentTracking.FulfillmentStatus.FULFILLED);
    }

    @Override
    @Transactional
    public List<PurchaseOrderFulfillmentTracking> getOutstandingOrders() {
        log.info("Warehouse manager requesting outstanding purchase orders");
        return repositoryPort.findByStatus(PurchaseOrderFulfillmentTracking.FulfillmentStatus.OUTSTANDING);
    }

    @Override
    @Transactional
    public List<PurchaseOrderFulfillmentTracking> getOrdersByCustomer(String customerNumber) {
        log.info("Warehouse manager requesting purchase orders for customer: {}", customerNumber);
        return repositoryPort.findByCustomerNumber(customerNumber);
    }

    @Override
    @Transactional
    public List<PurchaseOrderFulfillmentTracking> getAllOrders() {
        log.info("Warehouse manager requesting all purchase order fulfillments");
        return repositoryPort.findAll();
    }
} 