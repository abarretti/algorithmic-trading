package org.ab.core.slidingwindow;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlidingWindowTest {

    private final SlidingWindow slidingWindow = new SlidingWindow();

    @Test
    void shouldReturnMaxInKWindow() {
        long[] prices = {10, 5, 8, 12, 3, 6};

        List<Long> actual = slidingWindow.max(prices, 3);

        assertEquals(List.of(10L, 12L, 12L, 12L), actual);
    }

    @Test
    void worksWithWindowSizeOne() {
        long[] prices = {4, 2, 7};

        List<Long> result = slidingWindow.max(prices, 1);

        assertEquals(List.of(4L, 2L, 7L), result);
    }

    @Test
    void worksWhenWindowEqualsArrayLength() {
        long[] prices = {4, 2, 7};

        List<Long> result = slidingWindow.max(prices, 3);

        assertEquals(List.of(7L), result);
    }

    @Test
    void handlesRepeatedValues() {
        long[] prices = {5, 5, 5, 5};

        List<Long> result = slidingWindow.max(prices, 2);

        assertEquals(List.of(5L, 5L, 5L), result);
    }

    @Test
    void returnsEmptyWhenKIsLargerThanArray() {
        long[] prices = {1, 2};

        List<Long> result = slidingWindow.max(prices, 3);

        assertTrue(result.isEmpty());
    }
}
