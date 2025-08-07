package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.domain.PurchaseOrderLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderJpaMapper {
    
    public PurchaseOrderJpaEntity toJpaEntity(PurchaseOrder purchaseOrder) {
        PurchaseOrderJpaEntity jpaEntity = new PurchaseOrderJpaEntity();
        jpaEntity.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        jpaEntity.setPurchaseOrderNumber(purchaseOrder.getPurchaseOrderNumber());
        jpaEntity.setCustomerNumber(purchaseOrder.getCustomerNumber());
        jpaEntity.setCustomerName(purchaseOrder.getCustomerName());
        jpaEntity.setSellerId(purchaseOrder.getSellerId());
        jpaEntity.setSellerName(purchaseOrder.getSellerName());
        jpaEntity.setOrderDate(purchaseOrder.getOrderDate());
        jpaEntity.setStatus(purchaseOrder.getStatus());
        jpaEntity.setTotalValue(purchaseOrder.getTotalValue());
        
        // Map order lines
        List<PurchaseOrderLineJpaEntity> lineEntities = purchaseOrder.getOrderLines().stream()
                .map(line -> toLineJpaEntity(line, jpaEntity))
                .collect(Collectors.toList());
        
        jpaEntity.setOrderLines(lineEntities);
        return jpaEntity;
    }
    
    //Using the reconstruction constructor with exisiting ID and status
    public PurchaseOrder toDomain(PurchaseOrderJpaEntity jpaEntity) {
        List<PurchaseOrderLine> orderLines = jpaEntity.getOrderLines().stream()
                .map(this::toLineDomain)
                .collect(Collectors.toList());
        
        return new PurchaseOrder(
                jpaEntity.getPurchaseOrderId(),
                jpaEntity.getPurchaseOrderNumber(),
                jpaEntity.getCustomerNumber(),
                jpaEntity.getCustomerName(),
                jpaEntity.getSellerId(),
                jpaEntity.getSellerName(),
                jpaEntity.getOrderDate(),
                jpaEntity.getStatus(),
                orderLines
        );
    }
    
    private PurchaseOrderLineJpaEntity toLineJpaEntity(PurchaseOrderLine line, PurchaseOrderJpaEntity purchaseOrder) {
        PurchaseOrderLineJpaEntity lineEntity = new PurchaseOrderLineJpaEntity();
        lineEntity.setLineId(line.getLineId());
        lineEntity.setPurchaseOrder(purchaseOrder);
        lineEntity.setLineNumber(line.getLineNumber());
        lineEntity.setRawMaterialName(line.getRawMaterialName());
        lineEntity.setAmountInTons(line.getAmountInTons());
        lineEntity.setPricePerTon(line.getPricePerTon());
        return lineEntity;
    }
    
    private PurchaseOrderLine toLineDomain(PurchaseOrderLineJpaEntity lineEntity) {
        return new PurchaseOrderLine(
                lineEntity.getLineId(),
                lineEntity.getLineNumber(),
                lineEntity.getRawMaterialName(),
                lineEntity.getAmountInTons(),
                lineEntity.getPricePerTon()
        );
    }
} 