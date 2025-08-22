package be.kdg.prog6.invoicingContext.core;

import be.kdg.prog6.invoicingContext.ports.in.DeletePurchaseOrderUseCase;
import be.kdg.prog6.invoicingContext.ports.out.PurchaseOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeletePurchaseOrderUseCaseImpl implements DeletePurchaseOrderUseCase {

    private final PurchaseOrderRepositoryPort purchaseOrderRepositoryPort;

    @Override
    @Transactional
    public void deletePurchaseOrder(UUID purchaseOrderId) {
        log.info("Deleting purchase order: {}", purchaseOrderId);
        
        // Check if purchase order exists
        if (purchaseOrderRepositoryPort.findById(purchaseOrderId).isEmpty()) {
            throw new IllegalArgumentException("Purchase order not found: " + purchaseOrderId);
        }
        
        // Delete the purchase order
        purchaseOrderRepositoryPort.deleteById(purchaseOrderId);
        
        log.info("Purchase order deleted successfully: {}", purchaseOrderId);
    }
}