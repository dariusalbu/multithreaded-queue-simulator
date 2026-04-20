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
    private JScrollPane simulationJPanel;
    private JButton runButton;
    private JTextField maxArrivalTimeTextField;
    private JTextField minArrivalTimeJTextField;
    private JTextField maxProcessingTimeJTextField;
    private JTextField minProcessingTimeJTextField;
    private JTextField numberOfServersJTextField;
    private JTextField numberOfClientsJTextField;
    private JTextField timeLimitJTextField;

    public SimulationFrame() {
        setContentPane(mainJPanel);
        setTitle("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);


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

    }

    public void takeInputData() {

    }
}
