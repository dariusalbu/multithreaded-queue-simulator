package org.example.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private final BlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod;

    public Server() {
        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask) {
        this.tasks.add(newTask);
        this.waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = this.tasks.peek();

                if (task != null) {
                    while (task.getServiceTime() > 0) {
                        Thread.sleep(1000);

                        waitingPeriod.decrementAndGet();
                        task.setServiceTime(task.getServiceTime() - 1);
                    }
                    this.tasks.poll();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
}
