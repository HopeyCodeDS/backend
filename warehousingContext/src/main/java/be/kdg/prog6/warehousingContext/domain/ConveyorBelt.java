package be.kdg.prog6.warehousingContext.domain;

import be.kdg.prog6.common.domain.MaterialType;

public record ConveyorBelt(String beltNumber, MaterialType materialType) {

    @Override
    public String toString() {
        return "ConveyorBelt{" +
                "beltNumber='" + beltNumber + '\'' +
//                ", materialType=" + materialType +
                '}';
    }

    public String getBeltNumber() {
        return beltNumber;
    }

}
