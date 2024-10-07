package be.kdg.prog6.watersideContext.domain;

import be.kdg.prog6.common.domain.PersonId;

public class ShipCaptain {
    private PersonId shipCaptainId;
    private String name;
    private String shipName;

    public ShipCaptain(PersonId shipCaptainId, String name, String shipName) {
        this.shipCaptainId = shipCaptainId;
        this.name = name;
        this.shipName = shipName;
    }

    public PersonId getShipCaptainId() {
        return shipCaptainId;
    }

    public void setShipCaptainId(PersonId shipCaptainId) {
        this.shipCaptainId = shipCaptainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    @Override
    public String toString() {
        return "ShipCaptain{" +
                "shipCaptainId=" + shipCaptainId +
                ", name='" + name + '\'' +
                ", shipName='" + shipName + '\'' +
                '}';
    }


}