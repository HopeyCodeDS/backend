package be.kdg.prog6.watersideContext.ports.in;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CompleteLoadingUseCase {
    void completeLoading(UUID shippingOrderId, LocalDateTime loadingCompletionDate);
} 