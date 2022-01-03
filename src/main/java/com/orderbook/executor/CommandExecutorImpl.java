package com.orderbook.executor;

import com.orderbook.model.Order;
import com.orderbook.services.MarketOrderService;
import com.orderbook.services.QueriesService;
import com.orderbook.services.UpdatesService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CommandExecutorImpl implements CommandExecutor<Integer> {
    private QueriesService queriesService;
    private UpdatesService updatesService;
    private MarketOrderService marketOrderService;

    public CommandExecutorImpl(QueriesService queriesService, UpdatesService updatesService, MarketOrderService marketOrderService) {
        this.queriesService = queriesService;
        this.updatesService = updatesService;
        this.marketOrderService = marketOrderService;
    }

    @Override
    public List<Result<Integer>> execute(Collection<Command> commands) {
        List<Result<Integer>> results = new LinkedList<>();
        for (Command cmd : commands) {
            switch (cmd.getType()) {
                case O:
                    switch (cmd.getParamString(0)) {
                        case "buy":
                            marketOrderService.buy(cmd.getParamInt(1));
                            break;
                        case "sell":
                            marketOrderService.sell(cmd.getParamInt(1));
                            break;
                    }
                    break;
                case Q:
                    Result<Integer> result = new Result<>();
                    switch (cmd.getParamString(0)) {
                        case "best_bid":
                            Order bestBid = queriesService.getBestBid();
                            result.addParam(bestBid.getPrice());
                            result.addParam(bestBid.getSize());
                            break;
                        case "best_ask":
                            Order bestAsk = queriesService.getBestAsk();
                            result.addParam(bestAsk.getPrice());
                            result.addParam(bestAsk.getSize());
                            break;
                        case "size":
                            result.addParam(queriesService.getSize(cmd.getParamInt(1)));
                            break;
                    }
                    results.add(result);
                    break;
                case U:
                    switch (cmd.getParamString(2)) {
                        case "ask":
                            updatesService.updateOrder(cmd.getParam(0),
                                    cmd.getParam(1), Order.Type.ASK);
                            break;
                        case "bid":
                            updatesService.updateOrder(cmd.getParam(0),
                                    cmd.getParam(1), Order.Type.BID);
                            break;
                    }
                    break;
            }
        }
        return results;
    }
}
