package org.ab.core.topk;

public class SymbolVolume {
    private final String symbol;
    private final long volume;

    public SymbolVolume(String symbol, long volume) {
        this.symbol = symbol;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public long getVolume() {
        return volume;
    }
}
