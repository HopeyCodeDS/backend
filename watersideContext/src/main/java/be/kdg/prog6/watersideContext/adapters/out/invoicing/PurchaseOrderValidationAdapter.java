package be.kdg.prog6.watersideContext.adapters.out.invoicing;

import be.kdg.prog6.watersideContext.domain.PurchaseOrderValidationResult;
import be.kdg.prog6.watersideContext.ports.out.PurchaseOrderValidationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderValidationAdapter implements PurchaseOrderValidationPort {
    
    // Local cache of validated purchase orders from events
    private final Map<String, PurchaseOrderValidationResult> validatedPurchaseOrders = new ConcurrentHashMap<>();
    
    @Override
    public Optional<PurchaseOrderValidationResult> validatePurchaseOrder(String purchaseOrderReference, String customerNumber) {
        log.info("Foreman validating purchase order: {} for customer: {}", purchaseOrderReference, customerNumber);
        
        PurchaseOrderValidationResult cachedResult = validatedPurchaseOrders.get(purchaseOrderReference);
        
        if (cachedResult != null) {
            log.info("Found cached validation result for PO: {} - Customer: {}", purchaseOrderReference, cachedResult.getCustomerNumber());
            
            // Verify customer number matches
            if (cachedResult.getCustomerNumber().equals(customerNumber)) {
                log.info("PO validation successful: {} matches customer: {}", purchaseOrderReference, customerNumber);
                return Optional.of(cachedResult);
            } else {
                log.warn("Customer number mismatch for PO: {}. Expected: {}, Found: {}", 
                        purchaseOrderReference, customerNumber, cachedResult.getCustomerNumber());
                return Optional.empty();
            }
        }
        
        log.warn("Purchase order not found in cache: {}", purchaseOrderReference);
        return Optional.empty();
    }
    
    // Method to add validated PO from event consumption
    public void addValidatedPurchaseOrder(String purchaseOrderReference, String customerNumber, String customerName) {
        PurchaseOrderValidationResult result = new PurchaseOrderValidationResult(
            true,
            purchaseOrderReference,
            customerNumber,
            customerName,
            "Purchase order validated from event"
        );
        
        validatedPurchaseOrders.put(purchaseOrderReference, result);
        log.info("Added validated purchase order to foreman cache: {} for customer: {}", purchaseOrderReference, customerNumber);
    }
    
    // Method to get cache size for debugging
    public int getCacheSize() {
        return validatedPurchaseOrders.size();
    }
} 