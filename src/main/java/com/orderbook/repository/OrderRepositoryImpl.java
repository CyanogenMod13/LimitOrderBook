package com.orderbook.repository;

import com.orderbook.model.Order;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepository {
    private Map<Integer, Order> repo = new HashMap<>();
    private Order bestBid;
    private Order bestAsk;

    @Override
    public void add(Order order) {
        if (order.getSize() <= 0) return;
        repo.put(order.getPrice(), order);
        checkBest(order);
    }

    @Override
    public void remove(Order order) {
        repo.remove(order.getPrice());
        if (bestBid == order) {
            bestBid = null;
            for (Map.Entry<Integer, Order> entry : repo.entrySet())
                checkBestBid(entry.getValue());
        }
        if (bestAsk == order) {
            bestAsk = null;
            for (Map.Entry<Integer, Order> entry : repo.entrySet())
                checkBestAsk(entry.getValue());
        }
    }

    @Override
    public void update(Order order) {
        Order o = repo.get(order.getPrice());
        if (order.getSize() <= 0) {
            remove(o);
        } else {
            o.setSize(order.getSize());
            if (o.getType() != order.getType()) {
                o.setType(order.getType());
                bestAsk = bestBid = null;
                for (Map.Entry<Integer, Order> entry : repo.entrySet())
                    checkBest(entry.getValue());
            }
        }
    }

    @Override
    public Order findByPrice(int price) {
        return repo.get(price);
    }

    @Override
    public Order findBySize(int size) {
        for (Map.Entry<Integer, Order> entry : repo.entrySet())
            if (entry.getValue().getSize() == size)
                return entry.getValue();
        return null;
    }

    @Override
    public Order findByType(Order.Type type) {
        for (Map.Entry<Integer, Order> entry : repo.entrySet())
            if (entry.getValue().getType() == type)
                return entry.getValue();
        return null;
    }

    @Override
    public Collection<Order> findAll() {
        return repo.values();
    }

    @Override
    public Order findBestAsk() {
        return bestAsk;
    }

    @Override
    public Order findBestBid() {
        return bestBid;
    }

    private void checkBest(Order order) {
        checkBestBid(order);
        checkBestAsk(order);
    }

    private void checkBestBid(Order potential) {
        if (potential.getType() != Order.Type.BID) return;
        if (bestBid == null || potential.getPrice() > bestBid.getPrice())
            bestBid = potential;
    }

    private void checkBestAsk(Order potential) {
        if (potential.getType() != Order.Type.ASK) return;
        if (bestAsk == null || potential.getPrice() < bestAsk.getPrice())
            bestAsk = potential;
    }
}
