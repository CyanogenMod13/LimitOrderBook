package com.orderbook.services;

import com.orderbook.model.Order;
import com.orderbook.repository.OrderRepository;

public class MarketOrderService extends AbstractService{
    public MarketOrderService(OrderRepository repository) {
        super(repository);
    }

    public void buy(int size) {
        execute(repository.findBestAsk(), size);
    }

    public void sell(int size) {
        execute(repository.findBestBid(), size);
    }

    private void execute(Order order, int size) {
        order.setSize(order.getSize() - size);
        removeZeroSizeOrder(order);
    }

    private boolean isNonZeroSize(Order order) {
        return order.getSize() > 0;
    }

    private void removeZeroSizeOrder(Order order) {
        if (!isNonZeroSize(order)) {
            repository.remove(order);
        }
    }
}
