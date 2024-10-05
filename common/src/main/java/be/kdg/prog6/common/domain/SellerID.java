package be.kdg.prog6.common.domain;

import java.util.Objects;
import java.util.UUID;

public record SellerID(UUID uuid){
    public SellerID{
        Objects.requireNonNull(uuid);
    }
}