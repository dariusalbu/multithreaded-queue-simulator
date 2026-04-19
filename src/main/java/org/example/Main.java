package org.example;

import org.example.business.SimulationManager;

public class Main {
    public static void main(String[] args) {
        SimulationManager simulationManager = new SimulationManager();
        Thread t = new Thread(simulationManager);
        t.start();
    }
}