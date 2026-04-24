package org.ab.core.ordermatching;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMatchingEngineTest {

    private OrderMatchingEngine engine = new OrderMatchingEngine();

    @Test
    void shouldFill() {
        // Given
        Order buy = new Order(OrderType.BUY, 100, 5);
        Order sell = new Order(OrderType.SELL, 100, 5);
        List<Order> orders = List.of(buy, sell);
        engine.submitOrders(orders);

        // When
        List<OrderResult> actual = engine.match();

        // Then
        assertEquals(buy, actual.getFirst().getOrder());
        assertEquals(Result.FILL, actual.getFirst().getResult());
        assertEquals(sell, actual.get(1).getOrder());
        assertEquals(Result.FILL, actual.get(1).getResult());
        assertTrue(engine.buyOrders.isEmpty());
        assertTrue(engine.sellOrders.isEmpty());
    }

    @Test
    void shouldNotFill() {
        // Given
        Order buy = new Order(OrderType.BUY, 100, 5);
        Order sell = new Order(OrderType.SELL, 101, 5);
        List<Order> orders = List.of(buy, sell);
        engine.submitOrders(orders);

        // When
        List<OrderResult> actual = engine.match();

        // Then
        assertEquals(0, actual.size());
        assertEquals(1, engine.buyOrders.size());
        assertEquals(1, engine.sellOrders.size());
    }

    @Test
    void shouldPartiallyFill() {
        // Given
        Order buy = new Order(OrderType.BUY, 100, 10);
        Order sell = new Order(OrderType.SELL, 100, 3);
        List<Order> orders = List.of(buy, sell);
        engine.submitOrders(orders);

        // When
        List<OrderResult> actual = engine.match();

        // Then
        assertEquals(buy, actual.getFirst().getOrder());
        assertEquals(Result.PARTIAL_FILL, actual.getFirst().getResult());
        assertEquals(sell, actual.get(1).getOrder());
        assertEquals(Result.FILL, actual.get(1).getResult());

        assertEquals(7, engine.buyOrders.peek().getQuantity());
        assertTrue(engine.sellOrders.isEmpty());
    }

    @Test
    void shouldFillMultiOrders() {
        // Given
        Order buy = new Order(OrderType.BUY, 100, 10);
        Order sell1 = new Order(OrderType.SELL, 95, 3);
        Order sell2 = new Order(OrderType.SELL, 96, 4);
        Order sell3 = new Order(OrderType.SELL, 97, 5);
        List<Order> orders = List.of(buy, sell1, sell2, sell3);
        engine.submitOrders(orders);

        // When
        List<OrderResult> actual = engine.match();

        // Then
        assertEquals(sell1, actual.get(0).getOrder());
        assertEquals(Result.FILL, actual.get(0).getResult());
        assertEquals(sell2, actual.get(1).getOrder());
        assertEquals(Result.FILL, actual.get(1).getResult());
        assertEquals(buy, actual.get(2).getOrder());
        assertEquals(Result.FILL, actual.get(2).getResult());
        assertEquals(sell3, actual.get(3).getOrder());
        assertEquals(Result.PARTIAL_FILL, actual.get(3).getResult());

        assertTrue(engine.buyOrders.isEmpty());
        assertEquals(2, engine.sellOrders.peek().getQuantity());
        assertEquals(97, engine.sellOrders.peek().getPrice());
    }
}
