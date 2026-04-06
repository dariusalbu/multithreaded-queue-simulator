package org.example.business;

import org.example.model.Server;
import org.example.model.Task;

import java.util.List;

public class Scheduler {
    private List<Server>  servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public void changeStrategy(SelectionPolicy selectionPolicy) {

    }

    public void dispatchTask(Task task) {

    }

    public enum SelectionPolicy {
        SHORTEST_QUEUE,
        SHORTEST_TIME
    }
}
