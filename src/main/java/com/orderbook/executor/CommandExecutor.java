package com.orderbook.executor;

import java.util.Collection;
import java.util.List;

public interface CommandExecutor<T> {
    List<Result<T>> execute(Collection<Command> commands);
}
