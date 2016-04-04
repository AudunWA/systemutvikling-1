package no.ntnu.iie.stud.cateringstorm.gui;

import javax.swing.*;

/**
 * Created by EliasBrattli on 16/03/2016.
 */
public class DishInfoView extends JFrame{

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
    private JButton saveButton;
    private JButton removeRowButton;
    public DishInfoView() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        addRowButton.addActionListener(e-> {
            // TODO: Implement method addRow() that will open an empty table row, disabling save button until details are filled in
            //Consider using components
            addRow();
        });
        saveButton.addActionListener(e->{
            // TODO: Impement method saveChanges() that will send table into database
            saveChanges();
        });
        editRowButton.addActionListener(e->{
            // TODO: Implement method editRow() that will control the data input in the editable table cells
            editRow();
        });
        removeRowButton.addActionListener(e->{
            // TODO: Implement method removeRow() that will "remove" data from the view table. Data will still exist in database,
            removeRow();
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

    private boolean addRow(){
        return false;
    }
    private boolean saveChanges(){
        return false;
    }
    private void editRow(){

    }
    private boolean removeRow(){
        return false;
    }
    public static void main(String[]args){
        DishInfoView di = new DishInfoView();
        di.setVisible(true);
    }
}
