package org.ab.core.topk;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SymbolLeaderboardTest {

    private SymbolLeaderboard symbolLeaderboard = new SymbolLeaderboard();

    @Test
    void shouldRecordTradesInSequence() throws IOException {
        // Given
        String filePath = "/Users/abarretti/algorithmic-trading/src/test/resources/topk/trades.txt";

        // When
        symbolLeaderboard.recordTrades(filePath);

        // Then
        assertEquals(150, symbolLeaderboard.tradesbySymbol.get("AAPL"));
        assertEquals(130, symbolLeaderboard.tradesbySymbol.get("MSFT"));
        assertEquals(10, symbolLeaderboard.tradesbySymbol.get("GOOG"));
        assertEquals("AAPL", symbolLeaderboard.ranking.getFirst().getSymbol());
        assertEquals(150, symbolLeaderboard.ranking.getFirst().getVolume());
        assertEquals("GOOG", symbolLeaderboard.ranking.getLast().getSymbol());
        assertEquals(10, symbolLeaderboard.ranking.getLast().getVolume());

        // Given
        String filePath2 = "/Users/abarretti/algorithmic-trading/src/test/resources/topk/trades2.txt";

        // When
        symbolLeaderboard.recordTrades(filePath2);

        // Then
        assertEquals(210, symbolLeaderboard.tradesbySymbol.get("AAPL"));
        assertEquals(150, symbolLeaderboard.tradesbySymbol.get("MSFT"));
        assertEquals(40, symbolLeaderboard.tradesbySymbol.get("GOOG"));
        assertEquals(10, symbolLeaderboard.tradesbySymbol.get("INTL"));
        assertEquals("AAPL", symbolLeaderboard.ranking.getFirst().getSymbol());
        assertEquals(210, symbolLeaderboard.ranking.getFirst().getVolume());
        assertEquals("INTL", symbolLeaderboard.ranking.getLast().getSymbol());
        assertEquals(10, symbolLeaderboard.ranking.getLast().getVolume());
    }

    @Test
    void getTopK() throws IOException {
        // Given
        String filePath = "/Users/abarretti/algorithmic-trading/src/test/resources/topk/trades.txt";
        symbolLeaderboard.recordTrades(filePath);

        // When
        List<SymbolVolume> actual = symbolLeaderboard.getTopK(2);

        // Then
        assertEquals("AAPL", actual.get(0).getSymbol());
        assertEquals(150, actual.get(0).getVolume());
        assertEquals("MSFT", actual.get(1).getSymbol());
        assertEquals(130, actual.get(1).getVolume());
    }
}
