package be.kdg.prog6.invoicingContext.adapters.in.web.mapper;

import be.kdg.prog6.invoicingContext.adapters.in.web.dto.SubmitPurchaseOrderRequestDto;
import be.kdg.prog6.invoicingContext.adapters.in.web.dto.PurchaseOrderResponseDto;
import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.domain.commands.SubmitPurchaseOrderCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderMapper {
    
    public SubmitPurchaseOrderCommand toCommand(SubmitPurchaseOrderRequestDto requestDto) {
        List<SubmitPurchaseOrderCommand.PurchaseOrderLineCommand> orderLines = 
                requestDto.getOrderLines().stream()
                        .map(line -> new SubmitPurchaseOrderCommand.PurchaseOrderLineCommand(
                                line.getRawMaterialName(),
                                line.getAmountInTons(),
                                line.getPricePerTon()))
                        .collect(Collectors.toList());
        
        return new SubmitPurchaseOrderCommand(
                requestDto.getPurchaseOrderNumber(),
                requestDto.getCustomerNumber(),
                requestDto.getCustomerName(),
                orderLines
        );
    }
    
    public PurchaseOrderResponseDto toResponseDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDto responseDto = new PurchaseOrderResponseDto();
        responseDto.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        responseDto.setPurchaseOrderNumber(purchaseOrder.getPurchaseOrderNumber());
        responseDto.setCustomerNumber(purchaseOrder.getCustomerNumber());
        responseDto.setCustomerName(purchaseOrder.getCustomerName());
        responseDto.setOrderDate(purchaseOrder.getOrderDate());
        responseDto.setStatus(purchaseOrder.getStatus().name());
        responseDto.setTotalValue(purchaseOrder.getTotalValue());
        
        List<PurchaseOrderResponseDto.PurchaseOrderLineResponseDto> lineDtos = 
                purchaseOrder.getOrderLines().stream()
                        .map(this::toLineResponseDto)
                        .collect(Collectors.toList());
        
        responseDto.setOrderLines(lineDtos);
        return responseDto;
    }
    
    private PurchaseOrderResponseDto.PurchaseOrderLineResponseDto toLineResponseDto(
            be.kdg.prog6.invoicingContext.domain.PurchaseOrderLine line) {
        PurchaseOrderResponseDto.PurchaseOrderLineResponseDto lineDto = 
                new PurchaseOrderResponseDto.PurchaseOrderLineResponseDto();
        lineDto.setRawMaterialName(line.getRawMaterialName());
        lineDto.setAmountInTons(line.getAmountInTons());
        lineDto.setPricePerTon(line.getPricePerTon());
        lineDto.setLineTotal(line.getLineTotal());
        return lineDto;
    }
} 