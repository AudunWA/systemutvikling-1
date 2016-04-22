package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Information panel for chefs
 * Enables possibility to view and edit detail considering each order
 * Created by EliasBrattli on 16/03/2016.
 */
public class OrderInfoView extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel(0, 1);
    //Components
    private JTable ingredientTable;
    private JPanel mainPanel;
    private JButton editRowButton;
    private JList list1;
    private JButton addRowButton;
    private JPanel buttonPanel;
    private JButton exitButton;
    private JButton saveButton;

    public OrderInfoView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        addRowButton.addActionListener(e -> {
            addRow();
        });
    }

    public static void main(String[] args) {
        OrderInfoView oi = new OrderInfoView();
        oi.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // Fill table with dummy data
        Object[] columnNames = new Object[]{"Dish_ID", "Name", "Course", "Portions", "Preparation Time"};
        Object[][] data = new Object[][]{
                {1, "Happy hour soup", "Appetizer", 9, "15 min"},
                {2, "Kentucky Chicken Nuggets", "Main", 10, "30 min"},
                {3, "Bahama Banana split", "Dessert", 10, "20 min"},
        };
        //Adding object array to JTable
        ingredientTable = new JTable(data, columnNames);
    }

    private void addRow() {
        //Add a table row
    }

}

