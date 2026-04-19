package org.example.gui;

import org.example.business.Scheduler;
import org.example.model.Server;
import org.example.model.SimulationData;
import org.example.model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationFrame extends JFrame {
    private JPanel mainJPanel;
    private JLabel waitingClientsLabel;
    private JPanel waitingClientsJPanel;
    private JLabel timeJLabel;
    private JPanel queuesJPanel;
    private JTextField timeTextField;

    public SimulationFrame() {
        setContentPane(mainJPanel);
        setTitle("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update(Scheduler scheduler, List<Task> tasks, SimulationData simulationData) {
        timeJLabel.setText("Time: " + simulationData.getCurrentTime());

        waitingClientsJPanel.removeAll();
        for (Task taskItem : tasks) {
            JButton taskBox = new JButton(taskItem.toString());
            waitingClientsJPanel.add(taskBox);
        }

        waitingClientsJPanel.revalidate();
        waitingClientsJPanel.repaint();

        int queueCounter = 0;
        queuesJPanel.removeAll();
        queuesJPanel.setLayout(new BoxLayout(queuesJPanel, BoxLayout.Y_AXIS));
        for (Server server : scheduler.getServers()) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            queuesJPanel.add(panel);

            JButton taskBox = new JButton("Queue " + queueCounter);
            panel.add(taskBox);

            for (Task taskItem : server.getTasks()) {
                JButton taskBox2 = new JButton(taskItem.toString());
                panel.add(taskBox2);
            }

            queueCounter++;
        }

        queuesJPanel.revalidate();
        queuesJPanel.repaint();
    }
}
