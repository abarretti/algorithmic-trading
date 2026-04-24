package org.ab.core.ordermatching;

public class OrderResult {
    private final Order order;
    private final Result result;

    public OrderResult(Order order, Result result) {
        this.order = order;
        this.result = result;
    }


    public Order getOrder() {
        return order;
    }

    public Result getResult() {
        return result;
    }
}
