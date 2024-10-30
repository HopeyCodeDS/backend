package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.TruckDispatchRecordEntity;
import be.kdg.prog6.landsideContext.adapters.out.db.repositories.TruckDispatchRecordJpaRepository;
import be.kdg.prog6.landsideContext.domain.TruckDispatchRecord;
import be.kdg.prog6.landsideContext.ports.out.TruckDispatchRecordRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TruckDispatchRecordRepositoryAdapter implements TruckDispatchRecordRepositoryPort {

    private final TruckDispatchRecordJpaRepository repository;

    public TruckDispatchRecordRepositoryAdapter(TruckDispatchRecordJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(TruckDispatchRecord dispatchRecord) {
        TruckDispatchRecordEntity entity = new TruckDispatchRecordEntity(
                dispatchRecord.getLicensePlate(),
                dispatchRecord.getMaterialType(),
                dispatchRecord.getConveyorBeltNumber(),
                dispatchRecord.getWeighingBridgeNumber(),
                dispatchRecord.getWeight(),
                dispatchRecord.getDispatchTime()
        );
        repository.save(entity);
    }

    @Override
    public Optional<TruckDispatchRecord> findByLicensePlate(String licensePlate) {
        return repository.findByLicensePlate(licensePlate)
                .map(entity -> new TruckDispatchRecord(
                        entity.getLicensePlate(),
                        entity.getMaterialType(),
                        entity.getConveyorBeltNumber(),
                        entity.getWeighingBridgeNumber(),
                        entity.getWeight(),
                        entity.getDispatchTime()
                ));
    }
}
