package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.TruckJpaEntity;
import be.kdg.prog6.landsideContext.adapters.out.db.repositories.TruckJpaRepository;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TruckRepositoryAdapter implements TruckRepositoryPort {
    private static final Logger logger = LoggerFactory.getLogger(TruckRepositoryAdapter.class);

    private final TruckJpaRepository truckJpaRepository;

    public TruckRepositoryAdapter(TruckJpaRepository truckJpaRepository) {
        this.truckJpaRepository = truckJpaRepository;
    }

    @Override
    public Optional<Truck> findTruckByLicensePlate(String licensePlate) {
        logger.info("Find truck by license plate: {}", licensePlate);
        Optional<TruckJpaEntity> truckJpaEntity = truckJpaRepository.findByLicensePlate(licensePlate);

        // Manually map from TruckJpaEntity to Truck, including the weighed status
        return truckJpaEntity.map(truckEntity -> {
            Truck truck = new Truck(truckEntity.getLicensePlate(), truckEntity.getMaterialType());
            truck.setWeighed(truckEntity.isWeighed());
            truck.setWeighingBridgeNumber(truckEntity.getWeighingBridgeNumber());
            truck.setWarehouseID(truckEntity.getWarehouseID());
            truck.setWeight(truckEntity.getWeight());
            return truck;
        });
    }

    @Override
    public void save(Truck truck) {
        logger.info("Save truck: {}", truck);

        // Manually map from Truck to TruckJpaEntity
        TruckJpaEntity truckJpaEntity = new TruckJpaEntity();
        truckJpaEntity.setLicensePlate(truck.getLicensePlate());
        truckJpaEntity.setMaterialType(truck.getMaterialType());
        truckJpaEntity.setWeighingBridgeNumber(truck.getWeighingBridgeNumber());
        truckJpaEntity.setWeight(truck.getWeight());
        truckJpaEntity.setWarehouseID(truck.getWarehouseID());
        truckJpaEntity.setWeighed(truck.isWeighed());
        truckJpaEntity.setAssignedConveyorBelt(truck.getAssignedConveyorBelt());
        truckJpaEntity.setArrivalTime(truck.getArrivalTime());
        truckJpaEntity.setDocked(truck.isDocked());

//        truckJpaRepository.save(truckMapper.domainToEntity(truck));
        truckJpaRepository.save(truckJpaEntity);
        logger.info("Truck {} saved", truck.getLicensePlate());
    }
}
