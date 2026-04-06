package org.example.business;

import org.example.model.Server;
import org.example.model.Task;

import java.util.List;

public class ShortestTimeStrategy implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task task) {
        if (servers.isEmpty()) {
            return;
        }

        Server bestServer = servers.get(0);

        for (Server server : servers) {
            if (server.getWaitingPeriod().get() < bestServer.getWaitingPeriod().get()) {
                bestServer = server;
            }
        }

        bestServer.addTask(task);
    }
}
