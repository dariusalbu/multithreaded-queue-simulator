package org.example.model;

import org.example.business.Scheduler;

public class SimulationData {
    private int clientsServed;
    private float totalWaitingTime;
    private float totalServiceTime;
    private float maxServerSize;
    private int peakHour;
    private int currentTime;

    public SimulationData() {
        this.clientsServed = 0;
        this.totalWaitingTime = 0;
        this.totalServiceTime = 0;
        this.maxServerSize = 0;
        this.peakHour = 0;
        this.currentTime = 0;
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
