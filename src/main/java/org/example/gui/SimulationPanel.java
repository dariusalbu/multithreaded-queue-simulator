package org.example.gui;

import org.example.business.Scheduler;
import org.example.model.Server;
import org.example.model.SimulationData;
import org.example.model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationPanel extends JPanel {
    private Scheduler scheduler;
    private List<Task> tasks;

    private final int rectangleWidth = 80;
    private final int rectangleHeight = 30;

    public void updateData(Scheduler scheduler, List<Task> tasks) {
        this.scheduler = scheduler;
        this.tasks = tasks;

        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Waiting Clients: ", 0, 12);

        int x = 0;
        int y = 0;

        drawWaitingList(g2d, x, y);
        drawQueues(g2d, x, y);
    }

    public void drawWaitingList(Graphics2D g2d, int x,int y) {
        x = 0;
        y = 20;

        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {
                drawRectangle(g2d, x, y, task.toString());
                x += rectangleWidth + 30;
            }
        }
    }

    public void drawQueues(Graphics2D g2d, int x, int y) {
        x = 0;
        y += 60;
        int queueNumber = 1;

        if (scheduler != null) {
            for (Server server : scheduler.getServers()) {
                String queue = "Queue " + queueNumber + ": ";
                drawRectangle(g2d, x, y, queue);
                x += rectangleWidth + 30;

                for (Task task : server.getTasks()) {
                    drawRectangle(g2d, x, y, task.toString());
                    x += rectangleWidth + 30;
                }

                x = 0;
                y += rectangleHeight + 20;

                queueNumber++;
            }
        }
    }

    public void drawRectangle(Graphics2D g2d, int x, int y, String text) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y, rectangleWidth, rectangleHeight);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, rectangleWidth, rectangleHeight);

        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x + (rectangleWidth - g2d.getFontMetrics().stringWidth(text)) / 2, y + 20);
    }

    @Override
    public Dimension getPreferredSize() {
        int width = 0;
        int height = 0;

        if (scheduler != null) {
            int rows = (rectangleHeight + 20) * (scheduler.getServers().size() + 1);
            int cols = 0;
            int maxTasks = tasks.size();

            for (Server server : scheduler.getServers()) {
                if (maxTasks < server.getTasks().size()) {
                    maxTasks = server.getTasks().size();
                }
            }

            cols = (rectangleWidth + 30) * (maxTasks);

            if (rows > height) {
                height = rows;
            }

            if (cols > width) {
                width = cols;
            }
        }

        return new Dimension(width, height);
    }
}
