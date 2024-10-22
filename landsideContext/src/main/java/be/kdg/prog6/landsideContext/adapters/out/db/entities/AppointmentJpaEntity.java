package be.kdg.prog6.landsideContext.adapters.out.db.entities;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.domain.SellerID;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class AppointmentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "truck_license_plate", referencedColumnName = "license_plate", nullable = false)
    private TruckJpaEntity truck;

    @Column(name = "arrival_window", nullable = false)
    private LocalDateTime arrivalWindow;

    @Embedded
    private SellerID sellerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private SlotJpaEntity slot;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public TruckJpaEntity getTruck() {
        return truck;
    }

    public void setTruck(TruckJpaEntity truck) {
        this.truck = truck;
    }

    public LocalDateTime getArrivalWindow() {
        return arrivalWindow;
    }

    public void setArrivalWindow(LocalDateTime arrivalWindow) {
        this.arrivalWindow = arrivalWindow;
    }

    public SellerID getSellerId() {
        return sellerId;
    }

    public void setSellerId(SellerID sellerId) {
        this.sellerId = sellerId;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public SlotJpaEntity getSlot() {
        return slot;
    }

    public void setSlot(SlotJpaEntity slot) {
        this.slot = slot;
    }
}
