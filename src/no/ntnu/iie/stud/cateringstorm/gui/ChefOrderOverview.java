package no.ntnu.iie.stud.cateringstorm.gui;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.time.LocalDate;

/**
 * Created by Audun on 10.03.2016.
 */
public class ChefOrderOverview extends JFrame {
    private static final String WINDOW_TITLE = "Active orders";

    // Window dimensions
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JButton viewButton;
    private JButton setStateButton;
    private JComboBox comboBox1;

    public ChefOrderOverview() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here

        // Fill table with dummy data
        Object[] columnNames = new Object[]{"Order ID", "Package", "Portions", "Delivery time"};
        Object[][] data = new Object[][]{
                {1, "Dummy package 1", 4, LocalDate.now().plusDays(1)},
                {2, "Dummy package 2", 50, LocalDate.now().plusDays(2)},
                {3, "Dummy package 3", 50, LocalDate.now().plusDays(3)},
                {4, "Dummy package 4", 50, LocalDate.now().plusDays(4)},
                {5, "Dummy package 5", 50, LocalDate.now().plusDays(5)}
        };
        orderTable = new JTable(data, columnNames);

        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }
}
