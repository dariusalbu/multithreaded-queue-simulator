package org.example.model;

import org.example.business.Scheduler;

public class SimulationData {
    private int clientsServed;
    private float totalWaitingTime;
    private float totalServiceTime;
    private float maxServerSize;
    private int peakHour;
    private int currentTime;
    private int numberOfClients;
    private boolean simulationDone;
    private int waitingClientsNumber;

    public SimulationData(int numberOfClients) {
        this.numberOfClients = numberOfClients;
        this.clientsServed = 0;
        this.totalWaitingTime = 0;
        this.totalServiceTime = 0;
        this.maxServerSize = 0;
        this.peakHour = 0;
        this.currentTime = 0;
        this.waitingClientsNumber = 0;
        simulationDone = false;
    }

    public int getWaitingClientsNumber() {
        return waitingClientsNumber;
    }

    public void setWaitingClientsNumber(int waitingClientsNumber) {
        this.waitingClientsNumber = waitingClientsNumber;
    }

    public boolean isSimulationDone() {
        return simulationDone;
    }

    public void setSimulationDone(boolean simulationDone) {
        this.simulationDone = simulationDone;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getClientsServed() {
        return clientsServed;
    }

    public void setClientsServed(int clientsServed) {
        this.clientsServed = clientsServed;
    }

    public float getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(float totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public float getTotalServiceTime() {
        return totalServiceTime;
    }

    public void setTotalServiceTime(float totalServiceTime) {
        this.totalServiceTime = totalServiceTime;
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
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}
