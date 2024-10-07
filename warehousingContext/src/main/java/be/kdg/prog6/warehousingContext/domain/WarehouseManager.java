package be.kdg.prog6.warehousingContext.domain;

import be.kdg.prog6.common.domain.PersonId;

public class WarehouseManager {
    private PersonId managerId;
    private String name;

    public WarehouseManager(PersonId managerId, String name) {
        this.managerId = managerId;
        this.name = name;
    }

    public PersonId getManagerId() {
        return managerId;
    }

    public void setManagerId(PersonId managerId) {
        this.managerId = managerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
