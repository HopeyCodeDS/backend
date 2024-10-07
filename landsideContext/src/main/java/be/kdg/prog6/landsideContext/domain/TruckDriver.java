package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.PersonId;

public class TruckDriver {
    private PersonId driverId;
    private String driverName;
    private Truck truck;

    public TruckDriver(PersonId driverId, String driverName, Truck truck) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.truck = truck;
    }

    public PersonId getDriverId() {
        return driverId;
    }

    public void setDriverId(PersonId driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTruck() {
        return truck.getLicensePlate();
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @Override
    public String toString() {
        return "TruckDriver{" +
                "driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", truck=" + getTruck() +
                '}';
    }


}
