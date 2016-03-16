package no.ntnu.iie.stud.cateringstorm.gui;

import javax.swing.*;
import java.time.LocalDate;
/**
 * Information panel for chefs
 * Enables possibility to view and edit detail considering each order
 * Created by EliasBrattli on 16/03/2016.
 */
public class OrderInfo extends JFrame {
    private static final String WINDOW_TITLE = "Information";

    //Window dimensions
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    //Components
    private JTable ingredientTable;
    private JPanel mainPanel;
    private JButton editRowButton;
    private JList list1;
    private JButton addRowButton;
    private JPanel buttonPanel;
    private JButton exitButton;
    private JScrollPane tablePane;
    private JButton saveButton;

    public OrderInfo() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        // Fill table with dummy data
        Object[] columnNames = new Object[]{"Ingredient ID","Name", "Course", "Portions", "Preparation Time"};
        Object[][] data = new Object[][]{
                {1, "Happy hour soup","Appetizer", 9,"15 min" },
                {2, "Kentucky Chicken Nuggets","Main", 10,"30 min"},
                {3, "Bahama Banana split", "Dessert",10,"20 min"},
        };
        //Adding object array to JTable
        ingredientTable = new JTable(data, columnNames);
        ingredientTable.setFillsViewportHeight(true);

    }
}

