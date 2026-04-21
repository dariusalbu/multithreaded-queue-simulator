package org.example.business;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scheduler {
    private final List<Server> servers;
    private final int maxNoServers;
    private final int maxTasksPerServer;
    private Strategy strategy;
    private final ExecutorService executor;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<Server>();
        executor = Executors.newFixedThreadPool(maxNoServers);

        for (int i = 0; i < this.maxNoServers; i++) {
            Server server = new Server();
            servers.add(server);

            executor.submit(server);
        }
    }

    public void shutdown() {
        executor.shutdownNow();
    }

    public void changeStrategy(SelectionPolicy selectionPolicy) {
        if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }
        if (selectionPolicy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ShortestTimeStrategy();
        }
    }

    public void dispatchTask(Task task) {
        strategy.addTask(servers, task);
    }

    public Server getBestServer() {return strategy.getBestServer(servers);}

    public List<Server> getServers() {
        return servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public int getMaxTasksPerServer() {
        return maxTasksPerServer;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public enum SelectionPolicy {
        SHORTEST_QUEUE,
        SHORTEST_TIME
    }
}
