package no.ntnu.iie.stud.cateringstorm.gui.tabs;

<<<<<<< HEAD
=======
//import no.ntnu.iie.stud.cateringstorm.gui.statistics.ChartUtil;
>>>>>>> a8a8396121fcbc232cb6bf189d406db6cddbedfb
import no.ntnu.iie.stud.cateringstorm.gui.statistics.DeliveredOrdersChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StatisticsView");
        frame.setContentPane(new StatisticsView());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
