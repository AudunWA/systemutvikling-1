package no.ntnu.iie.stud.cateringstorm.gui.tabs;

/*import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;*/
import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddIngredientDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditDishDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;

import javax.swing.*;

import java.util.ArrayList;


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
    private JTable dishTable;
    private JPanel mainPanel;
    private JButton exitButton;
    private JScrollPane dishPane;

    private DishTableModel tableModel;

    public MenuAdministratorView() {
        add(mainPanel);
        exitButton.addActionListener(e-> {
            //Change window to dishinfo
        });
        editDishButton.addActionListener(e -> {
            int selectedRow = dishTable.getSelectedRow();
            if(selectedRow == -1) {
                return;
            }

            Dish dish = tableModel.getValue(selectedRow);

            EditDishDialog dialog = new EditDishDialog(dish);
            dialog.pack();
            dialog.setVisible(true);

            // TODO: Update table, see StorageView
        });

        dishTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();
    }
    private void createTable(){
        ArrayList<Dish> dishList = DishFactory.getAllDishes();
        Integer[] columns = new Integer[] { DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION }; // Columns can be changed
        tableModel = new DishTableModel(dishList, columns);
        dishTable = new JTable(tableModel);
        dishTable.setFillsViewportHeight(true);
    }

    public static void main(String[] args){
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new MenuAdministratorView());
        frame.setVisible(true);
        frame.setTitle(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }
}

