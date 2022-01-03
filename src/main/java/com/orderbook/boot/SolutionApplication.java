package com.orderbook.boot;

import com.orderbook.executor.CommandExecutorImpl;
import com.orderbook.executor.CommandReader;
import com.orderbook.executor.Result;
import com.orderbook.repository.OrderRepository;
import com.orderbook.repository.OrderRepositoryImpl;
import com.orderbook.services.MarketOrderService;
import com.orderbook.services.QueriesService;
import com.orderbook.services.UpdatesService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class SolutionApplication {
    public static void run() {
        OrderRepository repository = new OrderRepositoryImpl();

        MarketOrderService marketOrderService = new MarketOrderService(repository);
        QueriesService queriesService = new QueriesService(repository);
        UpdatesService updatesService = new UpdatesService(repository);

        CommandExecutorImpl executor = new CommandExecutorImpl(queriesService, updatesService, marketOrderService);
        CommandReader reader = new CommandReader(new File("input.txt"));
        List<Result<Integer>> results = executor.execute(reader.getCommands());

        StringBuilder stringBuilder = new StringBuilder();
        for (Result<Integer> result : results) {
            if (stringBuilder.length() != 0)
                stringBuilder.append(System.lineSeparator());
            stringBuilder.append(result);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
