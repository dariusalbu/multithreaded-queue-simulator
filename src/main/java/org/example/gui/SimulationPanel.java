package org.example.gui;

import org.example.business.Scheduler;
import org.example.model.SimulationData;
import org.example.model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationPanel extends JPanel {
    private Scheduler scheduler;
    private List<Task> tasks;
    SimulationData simulationData;

    public void updateData(Scheduler scheduler, List<Task> tasks, SimulationData simulationData) {
        this.scheduler = scheduler;
        this.tasks = tasks;
        this.simulationData = simulationData;
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Waiting Clients: ", 8, 20);

        int x = 30;
        int y = 30;



        drawRectangle(g2d, x, y, "random");
    }

    public void drawRectangle(Graphics2D g2d, int x, int y, String text) {
        int rectangleWidth = 60;
        int rectangleHeight = 30;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y, rectangleWidth, rectangleHeight);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, rectangleWidth, rectangleHeight);

        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x + 10, y + 20);
    }
}
