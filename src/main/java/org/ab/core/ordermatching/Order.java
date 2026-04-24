package org.ab.core.ordermatching;

public class Order {
    private final OrderType type;
    private final long price;
    private long quantity;

    public Order(OrderType type, long price, long quantity) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderType getType() {
        return type;
    }

    public long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
