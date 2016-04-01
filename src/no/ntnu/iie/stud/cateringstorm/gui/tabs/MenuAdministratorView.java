package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import javax.swing.*;

/**
 * Created by kenan on 01.04.2016.
 */
public class MenuAdministratorView extends JPanel {
    private static final String WINDOW_TITLE = "Menu Administrator";

    // Window dimensions
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private JButton addDishButton;
    private JButton editDishButton;
    private JButton removeDishButton;
    private JTable table1;
    private JPanel mainPanel;

    public MenuAdministratorView() {
        add(mainPanel);
    }
}

