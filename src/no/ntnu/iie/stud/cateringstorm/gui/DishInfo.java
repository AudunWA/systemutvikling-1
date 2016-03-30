package no.ntnu.iie.stud.cateringstorm.gui;

import javax.swing.*;

/**
 * Created by EliasBrattli on 16/03/2016.
 */
public class DishInfo extends JFrame{

    private static final String WINDOW_TITLE = "Information";

    //Window dimensions
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private JButton editRowButton;
    private JPanel mainPanel;
    private JButton addRowButton;
    private JTable ingredientTable;
    private JScrollPane tablePane;
    private JPanel buttonPanel;
    private JButton exitButton;
    private JButton saveButton;

    public DishInfo() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        addRowButton.addActionListener(e-> {
            //Add a table row
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        // Fill table with dummy data
        Object[] columnNames = new Object[]{"Meal ID", "Ingredient", "Quantity", "Unit"};
        Object[][] data = new Object[][]{
                {1, "Salt", 60,"g" },
                {2, "Chicken filet", 2000,"g"},
                {3, "Rice", 2000,"g"},
                {4, "Sauce", 10,"dL"},
                {5, "Pepper", 50, "g"},
                {6,"Lettuce",200,"g"}
        };
        ingredientTable = new JTable(data, columnNames);
        ingredientTable.setFillsViewportHeight(true);

    }



    public static void main(String[]args){
        DishInfo di = new DishInfo();
        di.setVisible(true);
    }
}
