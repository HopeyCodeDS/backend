package be.kdg.prog6.warehousingContext.domain;

import be.kdg.prog6.common.domain.MaterialType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ConveyorBelt {
    private final String beltNumber;
    private String warehouseId;
    private final MaterialType materialType;

    public ConveyorBelt(String beltNumber, String warehouseId, MaterialType materialType) {
        this.beltNumber = beltNumber;
        this.warehouseId = warehouseId;
        this.materialType = materialType;
    }

    public ConveyorBelt(String beltNumber, MaterialType materialType) {
        this.beltNumber = beltNumber;
        this.materialType = materialType;
    }

    @Override
    public String toString() {
        return "ConveyorBelt{" +
                "beltNumber='" + beltNumber + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", materialType=" + materialType +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ConveyorBelt) obj;
        return Objects.equals(this.beltNumber, that.beltNumber) &&
                Objects.equals(this.warehouseId, that.warehouseId) &&
                Objects.equals(this.materialType, that.materialType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beltNumber, warehouseId, materialType);
    }

}
