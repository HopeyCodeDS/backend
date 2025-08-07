package be.kdg.prog6.invoicingContext.core;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.domain.PurchaseOrderLine;
import be.kdg.prog6.invoicingContext.domain.commands.SubmitPurchaseOrderCommand;
import be.kdg.prog6.invoicingContext.ports.in.SubmitPurchaseOrderUseCase;
import be.kdg.prog6.invoicingContext.ports.out.PurchaseOrderRepositoryPort;
import be.kdg.prog6.invoicingContext.ports.out.PurchaseOrderSubmittedPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubmitPurchaseOrderUseCaseImpl implements SubmitPurchaseOrderUseCase {

    private final PurchaseOrderRepositoryPort purchaseOrderRepositoryPort;
    private final PurchaseOrderSubmittedPort purchaseOrderSubmittedPort;

    @Override
    public PurchaseOrder submitPurchaseOrder(SubmitPurchaseOrderCommand command) {
        log.info("Submitting purchase order: {} for customer: {}", command.getPurchaseOrderNumber(), command.getCustomerName());

        // Convert command lines to domain lines
        List<PurchaseOrderLine> orderLines = command.getOrderLines().stream()
                .map(line -> new PurchaseOrderLine(
                        line.getLineNumber(),
                        line.getRawMaterialName(),
                        line.getAmountInTons(),
                        line.getPricePerTon()))
                .collect(Collectors.toList());

        // Create purchase order
        PurchaseOrder purchaseOrder = new PurchaseOrder(
                command.getPurchaseOrderNumber(),
                command.getCustomerNumber(),
                command.getCustomerName(),
                command.getSellerId(),
                command.getSellerName(),
                command.getOrderDate(),
                orderLines
        );

        // Save to repository
        purchaseOrderRepositoryPort.save(purchaseOrder);

        // Publish event
        purchaseOrderSubmittedPort.publishPurchaseOrderSubmitted(purchaseOrder);

        log.info("Purchase order submitted successfully: {}", purchaseOrder.getPurchaseOrderId());
        return purchaseOrder;
    }
} 