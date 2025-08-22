package be.kdg.prog6.invoicingContext.core;

import be.kdg.prog6.invoicingContext.domain.CommissionFee;
import be.kdg.prog6.invoicingContext.ports.in.CommissionCalculationUseCase;
import be.kdg.prog6.invoicingContext.domain.commands.CalculateCommissionCommand;
import be.kdg.prog6.invoicingContext.ports.out.CommissionFeeCalculatedEventPublisherPort;
import be.kdg.prog6.invoicingContext.ports.out.CommissionFeeRepositoryPort;
import be.kdg.prog6.common.events.CommissionFeeCalculatedEvent;
import be.kdg.prog6.common.events.EventCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommissionCalculationUseCaseImpl implements CommissionCalculationUseCase {
    
    private final CommissionFeeRepositoryPort commissionFeeRepositoryPort;
    private final CommissionFeeCalculatedEventPublisherPort commissionFeeCalculatedEventPublisherPort;
    
    // This method is called when a commission fee is calculated
    @Override
    @Transactional
    public void calculateCommissionForFulfilledOrder(CalculateCommissionCommand command) {
        log.info("Calculating commission fee for fulfilled PO: {}", command.purchaseOrderNumber());
        
        // Calculate 1% commission on total order value
        double commissionAmount = CommissionFee.calculateCommissionAmount(command.totalValue());
        
        // Create commission fee record
        CommissionFee commissionFee = new CommissionFee(
            command.purchaseOrderNumber(),
            command.customerNumber(),
            command.sellerId(),
            commissionAmount,
            LocalDateTime.now()
        );
        
        // Save commission fee
        commissionFeeRepositoryPort.save(commissionFee);

        // Publish commission calculated event
        CommissionFeeCalculatedEvent event = new CommissionFeeCalculatedEvent(
            commissionFee.getCommissionFeeId(),
            commissionFee.getPurchaseOrderNumber(),
            commissionFee.getCustomerNumber(),
            commissionFee.getSellerId(),
            commissionFee.getAmount(),
            command.totalValue(),
            commissionFee.getCalculationDate()
        );
        commissionFeeCalculatedEventPublisherPort.publishCommissionFeeCalculatedEvent(event);
        
        log.info("Commission fee calculated: ${} for PO: {} (Total PO value: ${})", 
            commissionAmount, command.purchaseOrderNumber(), command.totalValue());

        log.info("{} for PO: {}", EventCatalog.INVOICE_GENERATED, command.purchaseOrderNumber());
    }
} 