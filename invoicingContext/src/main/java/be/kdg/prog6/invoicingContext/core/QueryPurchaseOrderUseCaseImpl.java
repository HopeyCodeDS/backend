package be.kdg.prog6.invoicingContext.core;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.ports.in.QueryPurchaseOrderUseCase;
import be.kdg.prog6.invoicingContext.ports.out.PurchaseOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryPurchaseOrderUseCaseImpl implements QueryPurchaseOrderUseCase {

    private final PurchaseOrderRepositoryPort purchaseOrderRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrder> getAllPurchaseOrders() {
        log.info("Retrieving all purchase orders");
        return purchaseOrderRepositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseOrder> getPurchaseOrderById(UUID purchaseOrderId) {
        log.info("Retrieving purchase order by ID: {}", purchaseOrderId);
        return purchaseOrderRepositoryPort.findById(purchaseOrderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrder> getOutstandingPurchaseOrders() {
        log.info("Retrieving outstanding purchase orders");
        return purchaseOrderRepositoryPort.findByStatus(PurchaseOrder.PurchaseOrderStatus.PENDING)
                .stream()
                .filter(po -> po.getStatus() == PurchaseOrder.PurchaseOrderStatus.PENDING || 
                              po.getStatus() == PurchaseOrder.PurchaseOrderStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrder> getFulfilledPurchaseOrders() {
        log.info("Retrieving fulfilled purchase orders");
        return purchaseOrderRepositoryPort.findByStatus(PurchaseOrder.PurchaseOrderStatus.FULFILLED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrder> getCancelledPurchaseOrders() {
        log.info("Retrieving cancelled purchase orders");
        return purchaseOrderRepositoryPort.findByStatus(PurchaseOrder.PurchaseOrderStatus.CANCELLED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrder> getPurchaseOrdersByCustomer(String customerId) {
        log.info("Retrieving purchase orders for customer: {}", customerId);
        return purchaseOrderRepositoryPort.findByCustomerNumber(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        log.info("Retrieving purchase orders by status: {}", status);
        try {
            PurchaseOrder.PurchaseOrderStatus orderStatus = PurchaseOrder.PurchaseOrderStatus.valueOf(status.toUpperCase());
            return purchaseOrderRepositoryPort.findByStatus(orderStatus);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid status provided: {}", status);
            return List.of();
        }
    }
}


// ## Summary

// I've implemented a complete PurchaseOrderController with all the required endpoints for the frontend Purchase Orders page. The implementation includes:

// ### **New Use Case Interfaces:**
// - `QueryPurchaseOrderUseCase` - For all read operations
// - `UpdatePurchaseOrderUseCase` - For update operations  
// - `DeletePurchaseOrderUseCase` - For delete operations

// ### **New Command Classes:**
// - `UpdatePurchaseOrderCommand` - For updating PO details
// - `UpdatePurchaseOrderStatusCommand` - For updating PO status

// ### **New DTOs:**
// - `UpdatePurchaseOrderRequestDto` - For PO update requests
// - `UpdatePurchaseOrderStatusRequestDto` - For status update requests

// ### **Enhanced Repository:**
// - Added `findByStatus()` and `deleteById()` methods
// - Extended JPA repository with status-based queries

// ### **Complete Controller Endpoints:**
// - **GET** `/invoicing/purchase-orders` - All POs
// - **GET** `/invoicing/purchase-orders/{id}` - PO by ID
// - **GET** `/invoicing/purchase-orders/outstanding` - Outstanding POs
// - **GET** `/invoicing/purchase-orders/fulfilled` - Fulfilled POs
// - **GET** `/invoicing/purchase-orders/cancelled` - Cancelled POs
// - **GET** `/invoicing/purchase-orders/by-customer/{customerId}` - POs by customer
// - **GET** `/invoicing/purchase-orders/by-status/{status}` - POs by status
// - **POST** `/invoicing/purchase-orders` - Create new PO
// - **PUT** `/invoicing/purchase-orders/{id}` - Update PO
// - **PATCH** `/invoicing/purchase-orders/{id}/status` - Update PO status
// - **DELETE** `/invoicing/purchase-orders/{id}` - Delete PO

// ### **Key Features:**
// - **Proper Security** - Role-based access control for different operations
// - **Comprehensive Error Handling** - Proper HTTP status codes and logging
// - **Transaction Management** - Read-only for queries, transactional for mutations
// - **Logging** - Detailed logging for debugging and monitoring
// - **Data Validation** - Input validation and business rule enforcement
// - **Hexagonal Architecture** - Clean separation between layers

// The implementation follows the existing codebase patterns and integrates seamlessly with the current architecture. All endpoints return data in the format expected by the frontend, including the complete purchase order structure with order lines, customer information, and fulfillment tracking data.
