package com.orderbook.executor;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private Type type;
    private List<Object> params = new ArrayList<>(3);

    public Command(Type type) {
        this.type = type;
    }

    public void addParam(Object param) {
        params.add(param);
    }

    public <T> T getParam(int index) {
        return (T) params.get(index);
    }

    public String getParamString(int index) {
        return getParam(index);
    }

    public int getParamInt(int index) {
        return getParam(index);
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        U, Q, O
    }
}
