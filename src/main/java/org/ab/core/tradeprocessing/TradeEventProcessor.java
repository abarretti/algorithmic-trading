package org.ab.core.tradeprocessing;

import java.util.List;
import java.util.Map;

public class TradeEventProcessor {

    class SymbolStats {
        private long totalQuantity;
        private long totalNotional;
        private long vwap;
    }

    public Map<String, SymbolStats> aggregateTrades(List<String> events) {
        return Map.of();
    }
}
