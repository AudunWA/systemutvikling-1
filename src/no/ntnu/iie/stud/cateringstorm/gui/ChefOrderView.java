package no.ntnu.iie.stud.cateringstorm.gui;

import javax.swing.*;
import java.time.LocalDate;

/**
 * Created by Audun on 10.03.2016.
 */
public class ChefOrderView extends JFrame {
    private static final String WINDOW_TITLE = "Active orders";

    // Window dimensions
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JPanel buttonPanel;
    private JButton viewButton;
    private JButton editButton;
    private JButton exitButton;
    private JButton saveButton;
    private JButton setStateButton;
    private JComboBox statusBox;
    private ComboBoxModel cbModel;

    public ChefOrderView() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setStateButton.addActionListener(e-> {
            //Change delivered status to an order by importing data from combobox
        });
        statusBox.addActionListener(e -> {

        });
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();

    }
    private void createTable(){
        // Fill table with dummy data
        Object[] columnNames = new Object[]{"Order ID", "Package", "Portions", "Delivery time"};
        Object[][] data = new Object[][]{
                {1, "Dummy package 1", 4, LocalDate.now().plusDays(1)},
                {2, "Dummy package 2", 20, LocalDate.now().plusDays(2)},
                {3, "Dummy package 3", 10, LocalDate.now().plusDays(3)},
                {4, "Dummy package 4", 50, LocalDate.now().plusDays(4)},
                {5, "Dummy package 5", 50, LocalDate.now().plusDays(5)}
        };
        orderTable = new JTable(data, columnNames);

        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }
    private void createComboBox(){
        Object[] status = {false,true};

        statusBox = new JComboBox(status);

    }
    public static void main(String[] args){
        ChauffeurOrderView coo = new ChauffeurOrderView();
        coo.setVisible(true);
    }
}
