package com.orderbook.executor;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private List<T> result = new ArrayList<>(2);

    public void addParam(T param) {
        result.add(param);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T param : result) {
            if (stringBuilder.length() != 0)
                stringBuilder.append(',');
            stringBuilder.append(param);
        }
        return stringBuilder.toString();
    }
}
