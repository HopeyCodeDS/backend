package be.kdg.prog6.landsideContext.ports.out;

public interface GateControlPort {
    void openGate();
    void closeGate();
    boolean isGateOpen();
} 