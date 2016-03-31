package no.ntnu.iie.stud.cateringstorm.gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
/**
 * Information panel for chefs
 * Enables possibility to view and edit detail considering each order
 * Created by EliasBrattli on 16/03/2016.
 */
public class OrderInfo extends JPanel {
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
    private final DefaultTableModel model = new DefaultTableModel(0, 1);

    public OrderInfo() {
        //setTitle(WINDOW_TITLE);
        //setSize(WIDTH, HEIGHT);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setContentPane(mainPanel);
        add(mainPanel);
        addRowButton.addActionListener(e-> {
            addRow();
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        // Fill table with dummy data
        Object[] columnNames = new Object[]{"Dish_ID","Name", "Course", "Portions", "Preparation Time"};
        Object[][] data = new Object[][]{
                {1, "Happy hour soup","Appetizer", 9,"15 min" },
                {2, "Kentucky Chicken Nuggets","Main", 10,"30 min"},
                {3, "Bahama Banana split", "Dessert",10,"20 min"},
        };
        //Adding object array to JTable
        ingredientTable = new JTable(data, columnNames);
        ingredientTable.setFillsViewportHeight(true);

    }
    private void addRow(){
        //Add a table row
    }
    public static void main(String[]args){
        OrderInfo oi = new OrderInfo();
        oi.setVisible(true);
    }

}

