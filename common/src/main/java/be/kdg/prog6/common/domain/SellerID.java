package be.kdg.prog6.common.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SellerID {
    private UUID uuid;

    // Default constructor needed by JPA
    protected SellerID() {}

    public SellerID(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerID sellerID = (SellerID) o;
        return Objects.equals(uuid, sellerID.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
