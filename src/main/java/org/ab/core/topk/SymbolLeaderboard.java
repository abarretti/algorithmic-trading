package org.ab.core.topk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SymbolLeaderboard {

    public Map<String, Long> tradesbySymbol = new HashMap<>();
    public NavigableSet<SymbolVolume> ranking = new TreeSet<>(
            (a, b) -> {
                int byVolume = Long.compare(b.getVolume(), a.getVolume());
                if (byVolume != 0) {
                    return byVolume;
                }
                return a.getSymbol().compareTo(b.getSymbol());
            });

    public void recordTrades(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            String symbol = st.nextToken();
            long quantity = Long.parseLong(st.nextToken());

            Long oldTotal = tradesbySymbol.get(symbol);
            if (oldTotal != null) {
                ranking.remove(new SymbolVolume(symbol, oldTotal));
            }

            long newTotal = tradesbySymbol.merge(symbol, quantity, Long::sum);
            ranking.add(new SymbolVolume(symbol, newTotal));
        }
    }

    public List<SymbolVolume> getTopK(int k) {
        List<SymbolVolume> topK = new ArrayList<>();
        int limit = Math.min(k, ranking.size());
        int count = 0;
        for (SymbolVolume symbolVolume : ranking) {
            topK.add(symbolVolume);
            count++;
            if (count == limit) {
                break;
            }
        }

        return topK;
    }
}
