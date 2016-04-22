package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import javax.swing.*;

/**
 * Created by kenan on 16.03.2016.
 */
public class AdministratorView extends JFrame {
    private static final String WINDOW_TITLE = "Employee Overview";

    private static final int WIDTH = 300;
    private static final int HEIGHT = 250;
    private JPanel mainPanel;
    private JLabel administratorOverview;
    private JList list1;

    public AdministratorView() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
    }
}
