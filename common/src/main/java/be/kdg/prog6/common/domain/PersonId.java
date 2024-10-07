package be.kdg.prog6.common.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record PersonId(UUID id){
    public PersonId{
        Objects.requireNonNull(id);
    }
}
