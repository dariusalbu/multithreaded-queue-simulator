package org.example.gui;

import org.example.business.Scheduler;
import org.example.model.SimulationData;
import org.example.model.Task;

import javax.swing.*;
import java.util.List;

public class SimulationFrame extends JFrame {
    private JPanel mainJPanel;

    public SimulationFrame() {
        setTitle("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update(Scheduler scheduler, List<Task> task, SimulationData simulationData) {

    }
}
