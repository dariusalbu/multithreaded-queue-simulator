package org.example.business;

import org.example.model.Server;
import org.example.model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task task) {
        if (servers.isEmpty()) {
            return;
        }

        Server bestServer = servers.get(0);

        for (Server server : servers) {
            if (server.getTasks().size() < bestServer.getTasks().size()) {
                bestServer = server;
            }
        }

        bestServer.addTask(task);
    }

    public Server getBestServer(List<Server> servers) {
        if (servers.isEmpty()) {
            return null;
        }

        Server bestServer = servers.get(0);

        for (Server server : servers) {
            if (server.getTasks().size() < bestServer.getTasks().size()) {
                bestServer = server;
            }
        }

        return bestServer;
    }
}
