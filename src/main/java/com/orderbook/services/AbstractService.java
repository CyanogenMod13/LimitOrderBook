package com.orderbook.services;

import com.orderbook.repository.OrderRepository;

public abstract class AbstractService {
    protected OrderRepository repository;

    public AbstractService(OrderRepository repository) {
        this.repository = repository;
    }
}
