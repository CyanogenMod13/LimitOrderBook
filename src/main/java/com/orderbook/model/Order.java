package com.orderbook.model;

import java.util.Objects;

public class Order {
    private int price;
    private int size;
    private Type type;

    public Order(int price, int size, Type type) {
        this.price = price;
        this.size = size;
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (price != order.price) return false;
        if (size != order.size) return false;
        return type == order.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, size, type);
    }

    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", size=" + size +
                ", type=" + type +
                '}';
    }

    public enum Type {
        BID("bid"), ASK("ask"), SPREAD("spread");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
