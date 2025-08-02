package be.kdg.prog6.warehousingContext.adapters.in.web;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.PurchaseOrderFulfillmentDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.dto.PurchaseOrderFulfillmentOverviewDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.mapper.PurchaseOrderFulfillmentMapper;
import be.kdg.prog6.warehousingContext.ports.in.GetPurchaseOrderFulfillmentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/warehouses/purchase-orders")
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderFulfillmentController {

    private final GetPurchaseOrderFulfillmentUseCase getPurchaseOrderFulfillmentUseCase;
    private final PurchaseOrderFulfillmentMapper mapper;

    @GetMapping("/fulfillment-overview")
    public ResponseEntity<PurchaseOrderFulfillmentOverviewDto> getFulfillmentOverview() {
        log.info("Warehouse manager requesting purchase order fulfillment overview");
        
        var fulfilledOrders = getPurchaseOrderFulfillmentUseCase.getFulfilledOrders();
        var outstandingOrders = getPurchaseOrderFulfillmentUseCase.getOutstandingOrders();
        
        var overview = mapper.toFulfillmentOverviewDto(fulfilledOrders, outstandingOrders);
        
        log.info("Warehouse manager overview: {} fulfilled, {} outstanding orders", 
                overview.getTotalFulfilled(), overview.getTotalOutstanding());
        
        return ResponseEntity.ok(overview);
    }

    @GetMapping("/fulfilled")
    public ResponseEntity<List<PurchaseOrderFulfillmentDto>> getFulfilledOrders() {
        log.info("Warehouse manager requesting fulfilled orders");
        var orders = getPurchaseOrderFulfillmentUseCase.getFulfilledOrders();
        var dtos = orders.stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/outstanding")
    public ResponseEntity<List<PurchaseOrderFulfillmentDto>> getOutstandingOrders() {
        log.info("Warehouse manager requesting outstanding orders");
        var orders = getPurchaseOrderFulfillmentUseCase.getOutstandingOrders();
        var dtos = orders.stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
} 