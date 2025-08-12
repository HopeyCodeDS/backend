package be.kdg.prog6.invoicingContext.adapters.in.web;

import be.kdg.prog6.invoicingContext.ports.in.StorageFeeCalculationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/invoicing/storage-fees")
@RequiredArgsConstructor
@Slf4j
public class StorageFeeController {

    private final StorageFeeCalculationUseCase storageFeeCalculationUseCase;

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateStorageFees(
            @RequestParam(required = false) 
            @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDate calculationDate) {
        
        try {
            LocalDate date = calculationDate != null ? calculationDate : LocalDate.now();
            log.info("Manually triggering storage fee calculation for date: {}", date);
            
            storageFeeCalculationUseCase.calculateDailyStorageFees(date);
            
            log.info("Storage fee calculation completed successfully for date: {}", date);
            return ResponseEntity.ok("Storage fees calculated successfully for " + date);
            
        } catch (Exception e) {
            log.error("Error during manual storage fee calculation: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                .body("Error calculating storage fees: " + e.getMessage());
        }
    }

    @PostMapping("/calculate-today")
    public ResponseEntity<String> calculateStorageFeesForToday() {
        return calculateStorageFees(LocalDate.now());
    }
}
