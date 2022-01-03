package com.orderbook.services;

import com.orderbook.model.Order;
import com.orderbook.repository.OrderRepository;

public class UpdatesService extends AbstractService {

    public UpdatesService(OrderRepository repository) {
        super(repository);
    }

    public void updateOrder(Order order) {
        if (repository.findByPrice(order.getPrice()) == null) {
            repository.add(order);
        } else {
            repository.update(order);
        }
    }

    public void updateOrder(int price, int size, Order.Type type) {
        updateOrder(new Order(price, size, type));
    }

    public void updateBidOrder(int price, int size) {
        updateOrder(price, size, Order.Type.BID);
    }

    public void updateAskOrder(int price, int size) {
        updateOrder(price, size, Order.Type.ASK);
    }

    public void updateSpreadOrder(int price, int size) {
        updateOrder(price, size, Order.Type.SPREAD);
    }
}
