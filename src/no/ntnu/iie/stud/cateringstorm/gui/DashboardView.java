package no.ntnu.iie.stud.cateringstorm.gui;

import javax.swing.*;

/**
 * The main view of the application.
 * Displayed after successful login, and contains all main subviews for specific employees.
 * Created by Audun on 31.03.2016.
 */
public class DashboardView extends JFrame {
    private static final String WINDOW_TITLE = "Dashboard";

    // Window dimensions
    private static final int WIDTH = 300;
    private static final int HEIGHT = 250;

    private JTabbedPane tabPane;
    private JPanel mainPanel;

    public DashboardView() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        fillTabPane();
        pack();
    }

    private void fillTabPane() {
        //tabPane.addTab("ChefOrderView", new ChefOrderView());
        tabPane.addTab("OrderInfo", new OrderInfo());
        tabPane.addTab("ChauffeurOrderView", new ChauffeurOrderView());
    }

    public static void main(String[] args) {
        // Makes the GUI same style as current OS :)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        DashboardView view = new DashboardView();
        view.setVisible(true);
    }
}
