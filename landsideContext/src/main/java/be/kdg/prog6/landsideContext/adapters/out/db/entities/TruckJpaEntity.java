package be.kdg.prog6.landsideContext.adapters.out.db.entities;

import be.kdg.prog6.common.domain.MaterialType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trucks")
public class TruckJpaEntity {

    @Id
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "warehouse_id")
    private String warehouseID;

    @Column(name = "arrival_window", nullable = false)
    private LocalDateTime arrivalWindow;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Column(name = "current_weighing_bridge_number")
    private String currentWeighingBridgeNumber;

    @Column(name = "assigned_conveyor_belt")
    private String assignedConveyorBelt;

    @Column(name = "weighed", nullable = false)
    private boolean weighed = false;

    // Getters and setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public LocalDateTime getArrivalWindow() {
        return arrivalWindow;
    }

    public void setArrivalWindow(LocalDateTime arrivalWindow) {
        this.arrivalWindow = arrivalWindow;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public String getCurrentWeighingBridgeNumber() {
        return currentWeighingBridgeNumber;
    }

    public void setCurrentWeighingBridgeNumber(String currentWeighingBridgeNumber) {
        this.currentWeighingBridgeNumber = currentWeighingBridgeNumber;
    }

    public String getAssignedConveyorBelt() {
        return assignedConveyorBelt;
    }

    public void setAssignedConveyorBelt(String assignedConveyorBelt) {
        this.assignedConveyorBelt = assignedConveyorBelt;
    }

    public boolean isWeighed() {
        return weighed;
    }

    public void setWeighed(boolean weighed) {
        this.weighed = weighed;
    }
}
