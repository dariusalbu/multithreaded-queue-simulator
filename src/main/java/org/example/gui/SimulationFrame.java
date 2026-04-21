package org.example.gui;

import org.example.business.Scheduler;
import org.example.business.SimulationManager;
import org.example.model.SimulationData;
import org.example.model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        simulationPanelJScrollPane.setViewportView(simulationPanel);
        simulationPanelJScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        simulationPanelJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

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
            averageServiceTimeLabel.setText("Average Waiting time: " + simulationData.getTotalWaitingTime() / simulationData.getWaitingClientsNumber());
            peakHourLabel.setText("Peak Hour: " + simulationData.getPeakHour());
        }
    }

    public void takeInputData() {
        String numberOfClientsString = numberOfClientsJTextField.getText();
        String  numberOfServersString = numberOfServersJTextField.getText();
        String  maxProcessingTimeString = maxProcessingTimeJTextField.getText();
        String  minProcessingTimeString = minProcessingTimeJTextField.getText();
        String maxArrivalTimeString = maxArrivalTimeTextField.getText();
        String minArrivalTimeString = minArrivalTimeJTextField.getText();
        String timeLimitString = timeLimitJTextField.getText();

        int numberOfClients = !numberOfClientsString.isEmpty() ? Integer.parseInt(numberOfClientsString) : 0;
        int numberOfServers = !numberOfClientsString.isEmpty() ? Integer.parseInt(numberOfServersString) : 0;
        int maxArrivalTime = !maxArrivalTimeString.isEmpty() ? Integer.parseInt(maxArrivalTimeString) : 0;
        int minArrivalTime = !minArrivalTimeString.isEmpty() ? Integer.parseInt(minArrivalTimeString) : 0;
        int maxProcessingTime = !maxProcessingTimeString.isEmpty() ? Integer.parseInt(maxProcessingTimeString) : 0;
        int minProcessingTime = !minProcessingTimeString.isEmpty() ? Integer.parseInt(minProcessingTimeString) : 0;
        int timeLimit = !timeLimitString.isEmpty() ? Integer.parseInt(timeLimitString) : 0;

        if (numberOfServers != 0) {
            SimulationManager simulationManager = new SimulationManager(
                    this,
                    numberOfClients,
                    numberOfServers,
                    timeLimit,
                    minArrivalTime,
                    maxArrivalTime,
                    minProcessingTime,
                    maxProcessingTime
            );


            Thread t = new Thread(simulationManager);
            t.start();
        }
    }
}
