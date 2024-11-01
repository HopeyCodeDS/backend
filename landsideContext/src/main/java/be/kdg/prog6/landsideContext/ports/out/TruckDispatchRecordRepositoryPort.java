package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.TruckDispatchRecord;
import java.util.Optional;

public interface TruckDispatchRecordRepositoryPort {
    void save(TruckDispatchRecord pdt);
    Optional<TruckDispatchRecord> findByLicensePlate(String licensePlate);
}
