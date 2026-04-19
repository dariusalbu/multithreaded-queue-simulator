package org.example.business;

import org.example.gui.SimulationFrame;
import org.example.model.Server;
import org.example.model.Task;

import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit = 60;
    public int maxArrivalTime = 30;
    public int minArrivalTime = 2;
    public int maxProcessingTime = 4;
    public int minProcessingTime = 2;
    public int numberOfServers = 2;
    public int numberOfClients = 4;

    private final Scheduler scheduler;
    private SimulationFrame frame;
    private final List<Task> tasks;
    private Scheduler.SelectionPolicy selectionPolicy = Scheduler.SelectionPolicy.SHORTEST_TIME;

    public SimulationManager() {
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.frame = new SimulationFrame();
        this.tasks = new ArrayList<Task>();
        this.scheduler.changeStrategy(selectionPolicy);

        generateRandomTasks();
    }

    public void generateRandomTasks() {
        Random random = new Random();

        for (int i = 0; i < this.numberOfClients; i++) {
            int arrivalTime = random.nextInt(this.maxArrivalTime -  this.minArrivalTime + 1) + this.minArrivalTime;
            int serviceTime = random.nextInt(this.maxProcessingTime - this.minProcessingTime + 1) + this.minProcessingTime;

            tasks.add(new Task(i + 1, arrivalTime, serviceTime));
        }

        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return Integer.compare(o1.getArrivalTime(), o2.getArrivalTime());
            }
        });
    }

    @Override
    public void run() {
        int currentTime = 0;
        float totalServiceTime = 0;
        int clientsServed = 0;
        float totalWaitingTime = 0;
        float maxServerSize = 0;
        int peakHour = 0;

        while (currentTime < timeLimit && !emptyWaitingLists()) {
            Iterator<Task> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(task);
                    iterator.remove();

                    totalServiceTime += task.getServiceTime();
                    clientsServed++;
                }
            }

            int serverSize = 0;
            for (Server server : scheduler.getServers()) {
                if (!server.getTasks().isEmpty()) {
                    serverSize += server.getTasks().size();
                }
            }

            if (serverSize > maxServerSize) {
                maxServerSize = serverSize;
                peakHour = currentTime;
            }

            totalWaitingTime += serverSize;

            System.out.println("Time " + currentTime);
            printWaitingTasks();
            printStatus();
            System.out.println();

            try {
                Thread.sleep(1000);
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            currentTime++;
        }

        System.out.println("Average Service Time: " + totalServiceTime / clientsServed);
        System.out.println("Total Waiting Time: " + totalWaitingTime / numberOfClients);
        System.out.println("Peak Hour: " + peakHour);

        scheduler.shutdown();
    }

    boolean emptyWaitingLists() {
        boolean empty = true;

        for (Server server : scheduler.getServers()) {
            if (!server.getTasks().isEmpty()) {
                empty = false;
            }
        }

        return empty && tasks.isEmpty();
    }

    void printWaitingTasks() {
        System.out.println("Waiting clients:");

        for(Task task : this.tasks) {
            if (this.tasks.indexOf(task) != 0) {
                System.out.print(", ");
            }
            System.out.print(task.toString());
        }

        if (!tasks.isEmpty()) {
            System.out.println();
        }
    }

    void printStatus() {
        for (int i = 0; i < scheduler.getServers().size(); i++) {
            System.out.println("Queue " + (i + 1) + ": " + scheduler.getServers().get(i).getTasks());
        }
    }
}
