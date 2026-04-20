package org.example.gui;

import org.example.business.Scheduler;
import org.example.model.SimulationData;
import org.example.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SimulationFrame extends JFrame {
    private JPanel mainJPanel;
    private JScrollPane dataInputJPanel;
    private JScrollPane simulationCardJPanel;
    private JButton runButton;
    private JTextField maxArrivalTimeTextField;
    private JTextField minArrivalTimeJTextField;
    private JTextField maxProcessingTimeJTextField;
    private JTextField minProcessingTimeJTextField;
    private JTextField numberOfServersJTextField;
    private JTextField numberOfClientsJTextField;
    private JTextField timeLimitJTextField;
    private JLabel currentTimeLabel;
    private JScrollPane simulationPanelJScrollPane;
    private JPanel statisticsJPanel;
    private JLabel averageServiceTimeLabel;
    private JLabel averageWaitingTimeLabel;
    private JLabel peakHourLabel;
    SimulationPanel simulationPanel;

    public SimulationFrame() {
        setContentPane(mainJPanel);
        setTitle("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        simulationPanel = new SimulationPanel();
        simulationPanel.setSize( 800, 600);
        simulationPanelJScrollPane.setViewportView(simulationPanel);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = (CardLayout) mainJPanel.getLayout();
                takeInputData();
                layout.next(mainJPanel);
            }
        });
    }

    public void update(Scheduler scheduler, List<Task> tasks, SimulationData simulationData) {
        currentTimeLabel.setText("Current Time: " + simulationData.getCurrentTime());

        simulationPanel.updateData(scheduler, tasks);

        System.out.println("Simulation Done: " + simulationData.isSimulationDone());

        if (simulationData.isSimulationDone()) {
            statisticsJPanel.setVisible(true);

            averageWaitingTimeLabel.setText("Average Service Time: " + simulationData.getTotalServiceTime() / simulationData.getNumberOfClients());
            averageServiceTimeLabel.setText("Average Waiting time: " + simulationData.getTotalWaitingTime() / simulationData.getNumberOfClients());
            peakHourLabel.setText("Peak Hour: " + simulationData.getPeakHour());
        }
    }

    public void takeInputData() {

    }
}
