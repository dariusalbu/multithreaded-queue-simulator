package org.example.model;

import com.sun.tools.attach.AttachPermission;
import org.example.business.Scheduler;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationData {
    private final AtomicInteger clientsServed;
    private final AtomicInteger waitingClientsNumber;
    private final AtomicInteger totalWaitingTime;
    private final AtomicInteger totalServiceTime;
    private final AtomicInteger currentTime;
    private final AtomicBoolean simulationDone;

    private float maxServerSize;
    private int peakHour;

    public SimulationData() {
        this.clientsServed = new AtomicInteger(0);
        this.totalWaitingTime = new AtomicInteger(0);
        this.totalServiceTime = new AtomicInteger(0);
        this.maxServerSize = 0;
        this.peakHour = 0;
        this.currentTime = new AtomicInteger(0);
        this.waitingClientsNumber = new AtomicInteger(0);
        simulationDone = new AtomicBoolean(false);
    }

    public synchronized void addServiceTime(int time) {
        this.totalServiceTime.addAndGet(time);
    }

    public synchronized void incrementClientsServed() {
        this.clientsServed.incrementAndGet();
    }

    public synchronized int getWaitingClientsNumber() {
        return waitingClientsNumber.get();
    }

    public void setWaitingClientsNumber(int waitingClientsNumber) {
        this.waitingClientsNumber.set(waitingClientsNumber);
    }

    public boolean isSimulationDone() {
        return simulationDone.get();
    }

    public void setSimulationDone(boolean simulationDone) {
        this.simulationDone.set(simulationDone);
    }

    public int getClientsServed() {
        return clientsServed.get();
    }

    public void setClientsServed(int clientsServed) {
        this.clientsServed.set(clientsServed);
    }

    public int getTotalWaitingTime() {
        return totalWaitingTime.get();
    }

    public void setTotalWaitingTime(int totalWaitingTime) {
        this.totalWaitingTime.set(totalWaitingTime);
    }

    public int getTotalServiceTime() {
        return totalServiceTime.get();
    }

    public void setTotalServiceTime(int totalServiceTime) {
        this.totalServiceTime.set(totalServiceTime);
    }

    public float getMaxServerSize() {
        return maxServerSize;
    }

    public void setMaxServerSize(float maxServerSize) {
        this.maxServerSize = maxServerSize;
    }

    public int getPeakHour() {
        return peakHour;
    }

    public void setPeakHour(int peakHour) {
        this.peakHour = peakHour;
    }

    public int getCurrentTime() {
        return currentTime.intValue();
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime.set(currentTime);
    }
}
