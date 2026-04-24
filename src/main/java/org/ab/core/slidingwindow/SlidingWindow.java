package org.ab.core.slidingwindow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SlidingWindow {

    public List<Long> max(long[] prices, int k) {
        List<Long> result = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < prices.length; i++) {
            // Remove indices that are out of this window
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // Remove smaller values from the back
            while (!deque.isEmpty() && prices[deque.peekLast()] <= prices[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            // Start recording answers once the first full window is formed
            if (i >= k - 1) {
                result.add(prices[deque.peekFirst()]);
            }
        }

        return result;
    }
}
