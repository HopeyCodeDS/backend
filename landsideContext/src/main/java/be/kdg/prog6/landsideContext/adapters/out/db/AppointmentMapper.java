package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.*;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    
    public AppointmentJpaEntity toJpaEntity(Appointment appointment) {
        AppointmentJpaEntity jpaEntity = new AppointmentJpaEntity();
        jpaEntity.setAppointmentId(appointment.getAppointmentId());
        jpaEntity.setSellerId(appointment.getSellerId());
        jpaEntity.setSellerName(appointment.getSellerName());
        jpaEntity.setArrivalWindowStart(appointment.getArrivalWindow().getStartTime());
        jpaEntity.setArrivalWindowEnd(appointment.getArrivalWindow().getEndTime());
        jpaEntity.setRawMaterialName(appointment.getRawMaterial().getName());
        jpaEntity.setRawMaterialPricePerTon(appointment.getRawMaterial().getPricePerTon());
        jpaEntity.setRawMaterialStoragePricePerTonPerDay(appointment.getRawMaterial().getStoragePricePerTonPerDay());
        jpaEntity.setStatus(appointment.getStatus());
        jpaEntity.setScheduledTime(appointment.getScheduledTime());
        
        // Map truck
        TruckJpaEntity truckJpaEntity = new TruckJpaEntity();
        truckJpaEntity.setTruckId(appointment.getTruck().getTruckId());
        truckJpaEntity.setLicensePlate(appointment.getTruck().getLicensePlate().getValue());
        truckJpaEntity.setTruckType(mapTruckType(appointment.getTruck().getTruckType()));
        truckJpaEntity.setCapacityInTons(appointment.getTruck().getMaxPayloadCapacity());
        
        jpaEntity.setTruck(truckJpaEntity);
        
        return jpaEntity;
    }
    
    public Appointment toDomain(AppointmentJpaEntity jpaEntity) {
        // Create domain objects
        LicensePlate licensePlate = new LicensePlate(jpaEntity.getTruck().getLicensePlate());
        RawMaterial rawMaterial = new RawMaterial(
            jpaEntity.getRawMaterialName(),
            jpaEntity.getRawMaterialPricePerTon(),
            jpaEntity.getRawMaterialStoragePricePerTonPerDay()
        );
        ArrivalWindow arrivalWindow = new ArrivalWindow(jpaEntity.getArrivalWindowStart());
        Truck truck = new Truck(
            jpaEntity.getTruck().getTruckId(),
            licensePlate,
            mapTruckType(jpaEntity.getTruck().getTruckType())
        );
        
        Appointment appointment = new Appointment(
            jpaEntity.getAppointmentId(),
            jpaEntity.getSellerId(),
            jpaEntity.getSellerName(),
            truck,
            rawMaterial,
            arrivalWindow,
            jpaEntity.getScheduledTime()
        );
        
        // Set status and arrival time
        appointment.setStatus(jpaEntity.getStatus());
        appointment.setScheduledTime(jpaEntity.getScheduledTime());
        
        return appointment;
    }
    
    private TruckJpaEntity.TruckType mapTruckType(Truck.TruckType domainType) {
        return switch (domainType) {
            case SMALL -> TruckJpaEntity.TruckType.SMALL;
            case MEDIUM -> TruckJpaEntity.TruckType.MEDIUM;
            case LARGE -> TruckJpaEntity.TruckType.LARGE;
        };
    }
    
    private Truck.TruckType mapTruckType(TruckJpaEntity.TruckType jpaType) {
        return switch (jpaType) {
            case SMALL -> Truck.TruckType.SMALL;
            case MEDIUM -> Truck.TruckType.MEDIUM;
            case LARGE -> Truck.TruckType.LARGE;
        };
    }
} 