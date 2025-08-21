package be.kdg.prog6.invoicingContext.core;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.domain.PurchaseOrderLine;
import be.kdg.prog6.invoicingContext.domain.commands.UpdatePurchaseOrderCommand;
import be.kdg.prog6.invoicingContext.domain.commands.UpdatePurchaseOrderStatusCommand;
import be.kdg.prog6.invoicingContext.ports.in.UpdatePurchaseOrderUseCase;
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
public class UpdatePurchaseOrderUseCaseImpl implements UpdatePurchaseOrderUseCase {

    private final PurchaseOrderRepositoryPort purchaseOrderRepositoryPort;

    @Override
    @Transactional
    public PurchaseOrder updatePurchaseOrder(UpdatePurchaseOrderCommand command) {
        log.info("Updating purchase order: {}", command.getPurchaseOrderId());
        
        Optional<PurchaseOrder> existingOrderOpt = purchaseOrderRepositoryPort.findById(command.getPurchaseOrderId());
        if (existingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Purchase order not found: " + command.getPurchaseOrderId());
        }
        
        PurchaseOrder existingOrder = existingOrderOpt.get();
        
        // Convert command lines to domain lines
        List<PurchaseOrderLine> orderLines = command.getOrderLines().stream()
                .map(line -> new PurchaseOrderLine(
                        line.getLineNumber(),
                        line.getRawMaterialName(),
                        line.getAmountInTons(),
                        line.getPricePerTon()))
                .collect(Collectors.toList());
        
        // Create updated purchase order
        PurchaseOrder updatedOrder = new PurchaseOrder(
                command.getPurchaseOrderId(),
                command.getPurchaseOrderNumber(),
                command.getCustomerNumber(),
                command.getCustomerName(),
                command.getSellerId(),
                command.getSellerName(),
                command.getOrderDate(),
                existingOrder.getStatus(), // Preserve current status
                orderLines
        );
        
        // Save updated order
        purchaseOrderRepositoryPort.save(updatedOrder);
        
        log.info("Purchase order updated successfully: {}", updatedOrder.getPurchaseOrderId());
        return updatedOrder;
    }

    @Override
    @Transactional
    public PurchaseOrder updatePurchaseOrderStatus(UpdatePurchaseOrderStatusCommand command) {
        log.info("Updating status for purchase order: {} to {}", command.getPurchaseOrderId(), command.getStatus());
        
        Optional<PurchaseOrder> existingOrderOpt = purchaseOrderRepositoryPort.findById(command.getPurchaseOrderId());
        if (existingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Purchase order not found: " + command.getPurchaseOrderId());
        }
        
        PurchaseOrder existingOrder = existingOrderOpt.get();
        
        try {
            PurchaseOrder.PurchaseOrderStatus newStatus = PurchaseOrder.PurchaseOrderStatus.valueOf(command.getStatus().toUpperCase());
            
            // Create updated purchase order with new status
            PurchaseOrder updatedOrder = new PurchaseOrder(
                    existingOrder.getPurchaseOrderId(),
                    existingOrder.getPurchaseOrderNumber(),
                    existingOrder.getCustomerNumber(),
                    existingOrder.getCustomerName(),
                    existingOrder.getSellerId(),
                    existingOrder.getSellerName(),
                    existingOrder.getOrderDate(),
                    newStatus,
                    existingOrder.getOrderLines()
            );
            
            // Save updated order
            purchaseOrderRepositoryPort.save(updatedOrder);
            
            log.info("Purchase order status updated successfully: {} to {}", updatedOrder.getPurchaseOrderId(), newStatus);
            return updatedOrder;
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + command.getStatus());
        }
    }
}