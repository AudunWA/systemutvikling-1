package no.ntnu.iie.stud.cateringstorm.gui.tabs;


import no.ntnu.iie.stud.cateringstorm.gui.statistics.DeliveredOrdersChart;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Displays various statistics about the system.
 * Created by Audun on 14.04.2016.
 */
public class StatisticsView extends JPanel {
    private JTabbedPane tabPane;
    private JPanel mainPanel;

    public StatisticsView() {
        super();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        tabPane.addTab("Order deliveries by day", new DeliveredOrdersChart());
        tabPane.addTab("Order placements by day (week)", DeliveredOrdersChart.generateOrdersOrderedChart(LocalDate.now().minusDays(7), LocalDate.now()));
        tabPane.addTab("Order placements by day (month)", DeliveredOrdersChart.generateOrdersOrderedChart(LocalDate.now().minusDays(30), LocalDate.now()));
        tabPane.addTab("Income by day (week)", DeliveredOrdersChart.generateIncomeChart(LocalDate.now().minusDays(7), LocalDate.now()));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StatisticsView");
        frame.setContentPane(new StatisticsView());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
