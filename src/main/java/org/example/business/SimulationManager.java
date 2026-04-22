package org.example.business;

import org.example.gui.SimulationFrame;
import org.example.model.Server;
import org.example.model.SimulationData;
import org.example.model.Task;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class SimulationManager implements Runnable {
    private final PrintWriter out;

    private final int timeLimit;
    private final int maxArrivalTime;
    private final int minArrivalTime;
    private final int maxProcessingTime;
    private final int minProcessingTime;
    private final int numberOfClients;

    private final SimulationFrame frame;
    private final Scheduler scheduler;
    private final SimulationData simulationData;
    private final List<Task> tasks;

    public SimulationManager(SimulationFrame simulationFrame, int numberOfClients, int numberOfServers, Scheduler.SelectionPolicy selectionPolicy, int timeLimit, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime) throws FileNotFoundException {
        this.numberOfClients = numberOfClients;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.timeLimit = timeLimit;
        this.simulationData = new SimulationData();
        this.scheduler = new Scheduler(numberOfServers, numberOfClients, simulationData);
        this.tasks = new ArrayList<Task>();
        this.frame = simulationFrame;
        scheduler.changeStrategy(selectionPolicy);
        String outputFileName = "log.txt";
        this.out = new PrintWriter(outputFileName);

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
        while (simulationData.getCurrentTime() < timeLimit) {
            manageTask(simulationData.getCurrentTime());
            computePeakHour(simulationData.getCurrentTime());

            if (emptyWaitingLists()) {
                break;
            }

            frame.update(scheduler, tasks, simulationData);
            writeInOutputFile();

            try {
                Thread.sleep(1000);
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            simulationData.setCurrentTime(simulationData.getCurrentTime() + 1);
        }

        simulationData.setSimulationDone(true);
        frame.update(scheduler, tasks, simulationData);
        writeInOutputFile();

        out.println("Average Service Time: " + (float)simulationData.getTotalServiceTime() / simulationData.getClientsServed());
        out.println("Average Waiting Time: " + (float)simulationData.getTotalWaitingTime() / simulationData.getWaitingClientsNumber());
        out.println("Peak Hour: " + simulationData.getPeakHour());

        out.close();
        scheduler.shutdown();
    }

    void writeInOutputFile() {
        out.println("Time " + simulationData.getCurrentTime());
        printWaitingTasks();
        printStatus();
        out.println();
    }

    void manageTask(int currentTime) {
        Iterator<Task> iterator = tasks.iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getArrivalTime() == currentTime) {
                Server server = scheduler.getBestServer();
                if (server != null) {
                    simulationData.setWaitingClientsNumber(simulationData.getWaitingClientsNumber() + 1);
                    int queueServiceTime = 0;
                    for (Task t : server.getTasks()) {
                        queueServiceTime += t.getServiceTime();
                    }
                    simulationData.setTotalWaitingTime(simulationData.getTotalWaitingTime() + queueServiceTime);
                }
                scheduler.dispatchTask(task);
                iterator.remove();
            }
        }
    }

    void computePeakHour(int currentTime) {
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
    }

    boolean emptyWaitingLists() {
        boolean empty = true;

        for (Server server : scheduler.getServers()) {
            if (!server.getTasks().isEmpty()) {
                empty = false;
                break;
            }
        }

        return empty && tasks.isEmpty();
    }

    void printWaitingTasks() {
        out.println("Waiting clients:");

        for(Task task : this.tasks) {
            if (this.tasks.indexOf(task) != 0) {
                out.print(", ");
            }
            out.print(task.toString());
        }

        if (!tasks.isEmpty()) {
            out.println();
        }
    }

    void printStatus() {
        for (int i = 0; i < scheduler.getServers().size(); i++) {
            out.println("Queue " + (i + 1) + ": " + scheduler.getServers().get(i).getTasks());
        }
    }
}
