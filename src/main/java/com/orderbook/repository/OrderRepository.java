package com.orderbook.repository;

import com.orderbook.model.Order;

import java.util.Collection;

public interface OrderRepository {
    void add(Order order);
    void remove(Order order);
    void update(Order order);

    Order findByPrice(int price);
    Order findBySize(int size);
    Order findByType(Order.Type type);
    Collection<Order> findAll();

    Order findBestAsk();
    Order findBestBid();
}
