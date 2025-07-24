package be.kdg.prog6.landsideContext.domain;

import lombok.Value;

@Value
public class LicensePlate {
    String value;
    
    public LicensePlate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        this.value = value.trim().toUpperCase();
    }
    
    @Override
    public String toString() {
        return value;
    }
} 