package be.kdg.prog6.landsideContext.domain;

import lombok.Value;

@Value
public class RawMaterial {
    String name;
    double pricePerTon;
    double storagePricePerTonPerDay;
    
    public static RawMaterial GYPSUM = new RawMaterial("Gypsum", 13.0, 1.0);
    public static RawMaterial IRON_ORE = new RawMaterial("Iron Ore", 110.0, 5.0);
    public static RawMaterial CEMENT = new RawMaterial("Cement", 95.0, 3.0);
    public static RawMaterial PETCOKE = new RawMaterial("Petcoke", 210.0, 10.0);
    public static RawMaterial SLAG = new RawMaterial("Slag", 160.0, 7.0);
    
    public static RawMaterial fromName(String name) {
        return switch (name.toLowerCase()) {
            case "gypsum" -> GYPSUM;
            case "iron ore", "ironore", "iron_ore" -> IRON_ORE;
            case "cement" -> CEMENT;
            case "petcoke" -> PETCOKE;
            case "slag" -> SLAG;
            default -> throw new IllegalArgumentException("Unknown raw material: " + name);
        };
    }
} 