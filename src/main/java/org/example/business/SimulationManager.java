package org.example.business;

import org.example.gui.SimulationFrame;
import org.example.model.Server;
import org.example.model.SimulationData;
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
    SimulationData simulationData;
    private SimulationFrame frame;
    private final List<Task> tasks;
    private Scheduler.SelectionPolicy selectionPolicy = Scheduler.SelectionPolicy.SHORTEST_TIME;

    public SimulationManager() {
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.simulationData = new SimulationData();
        this.frame = new SimulationFrame();
        this.tasks = new ArrayList<Task>();
        scheduler.changeStrategy(selectionPolicy);

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
        boolean running = true;

        while (simulationData.getCurrentTime() < timeLimit) {
            manageTask(simulationData.getCurrentTime());
            computeWaitingTimeAndPeakHour(simulationData.getCurrentTime());

            frame.update(scheduler, tasks, simulationData);
            System.out.println("Time " + simulationData.getCurrentTime());
            printWaitingTasks();
            printStatus();
            System.out.println();

            try {
                Thread.sleep(1000);
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            simulationData.setCurrentTime(simulationData.getCurrentTime() + 1);

            if (emptyWaitingLists() && running) {
                timeLimit = simulationData.getCurrentTime() + 1;
                running = false;
            }
        }

        System.out.println("Average Service Time: " + simulationData.getTotalServiceTime() / simulationData.getClientsServed());
        System.out.println("Total Waiting Time: " + simulationData.getTotalWaitingTime() / numberOfClients);
        System.out.println("Peak Hour: " + simulationData.getPeakHour());

        scheduler.shutdown();
    }

    void manageTask(int currentTime) {
        Iterator<Task> iterator = tasks.iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getArrivalTime() == currentTime) {
                scheduler.dispatchTask(task);
                iterator.remove();

                simulationData.setTotalServiceTime(simulationData.getTotalServiceTime() + task.getServiceTime());
                simulationData.setClientsServed(simulationData.getClientsServed() + 1);
            }
        }
    }

    void computeWaitingTimeAndPeakHour(int currentTime) {
        int serverSize = 0;

        for (Server server : scheduler.getServers()) {
            if (!server.getTasks().isEmpty()) {
                serverSize += server.getTasks().size();
            }
        }

        if (serverSize > simulationData.getMaxServerSize()) {
            simulationData.setMaxServerSize(serverSize);
            simulationData.setPeakHour(currentTime);
        }

        simulationData.setTotalWaitingTime(simulationData.getTotalWaitingTime() + serverSize);
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
