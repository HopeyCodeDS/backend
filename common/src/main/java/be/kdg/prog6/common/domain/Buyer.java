package be.kdg.prog6.common.domain;

public class Buyer {
    private String buyerId;
    private String name;

    public Buyer(String buyerId, String name) {
        this.buyerId = buyerId;
        this.name = name;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getName() {
        return name;
    }
}

