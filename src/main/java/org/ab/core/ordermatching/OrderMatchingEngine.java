package org.ab.core.ordermatching;

import java.util.*;

public class OrderMatchingEngine {

    /**
     * This would never be public. This is just so they're exposed for testing
     *
     */
    public Queue<Order> buyOrders = new PriorityQueue<>((a, b) -> Long.compare(b.getPrice(), a.getPrice()));
    public Queue<Order> sellOrders = new PriorityQueue<>(Comparator.comparingLong(Order::getPrice));

    public void submitOrders(List<Order> orders) {
        orders.forEach(order -> {
            if (order.getType().equals(OrderType.BUY)) {
                buyOrders.offer(order);
            } else if (order.getType().equals(OrderType.SELL)) {
                sellOrders.offer(order);
            }
        });
    }

    public List<OrderResult> match() {
        List<OrderResult> results = new LinkedList<>();
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()
                && buyOrders.peek().getPrice() >= sellOrders.peek().getPrice()) {

            Order buy = buyOrders.peek();
            Order sell = sellOrders.peek();

            long tradeQty = Math.min(buy.getQuantity(), sell.getQuantity());

            buy.setQuantity(buy.getQuantity() - tradeQty);
            sell.setQuantity(sell.getQuantity() - tradeQty);

            if (buy.getQuantity() == 0) {
                results.add(new OrderResult(buy, Result.FILL));
                buyOrders.poll();
            } else {
                results.add(new OrderResult(buy, Result.PARTIAL_FILL));
            }

            if (sell.getQuantity() == 0) {
                results.add(new OrderResult(sell, Result.FILL));
                sellOrders.poll();
            } else {
                results.add(new OrderResult(sell, Result.PARTIAL_FILL));
            }
        }

        return filterZeroQuantityPartialFills(results);
    }

    private List<OrderResult> filterZeroQuantityPartialFills(List<OrderResult> results) {
        return results.stream()
                .filter(result -> !(result.getResult().equals(Result.PARTIAL_FILL) && result.getOrder().getQuantity() == 0))
                .toList();
    }
}
