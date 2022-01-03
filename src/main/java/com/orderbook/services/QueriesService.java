package com.orderbook.services;

import com.orderbook.model.Order;
import com.orderbook.repository.OrderRepository;

public class QueriesService extends AbstractService {
    public QueriesService(OrderRepository repository) {
        super(repository);
    }

    public Order getBestBid() {
        Order order = repository.findBestBid();
        if (order != null) return order;
        return new Order(0, 0, Order.Type.BID);
    }

    public Order getBestAsk() {
        Order order = repository.findBestAsk();
        if (order != null) return order;
        return new Order(0, 0, Order.Type.ASK);
    }

    public int getSize(int price) {
        Order order = repository.findByPrice(price);
        if (order != null) return order.getSize();
        return 0;
    }
}
