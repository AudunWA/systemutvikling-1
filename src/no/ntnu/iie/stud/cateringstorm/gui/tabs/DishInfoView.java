package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by EliasBrattli on 16/03/2016.
 * Fixed by Kenan on 08.04.16
 */
public class DishInfoView extends JPanel{
    private JButton editRowButton;
    private JPanel mainPanel;
    private JButton addRowButton;
    private JTable ingredientTable;
    private JScrollPane tablePane;
    private JPanel buttonPanel;
    private JButton saveButton;
    private JButton viewIngredientsButton;

    private IngredientTableModel tableModel;
    private Dish dish;

    public DishInfoView(Dish dish) {
        this.dish = dish;

        add(mainPanel);

        viewIngredientsButton.addActionListener(e -> {
            int selectedRow = ingredientTable.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            Ingredient ingredient = tableModel.getValue(selectedRow);

            //TODO Lag en ViewDishDialog

            //ViewDishDialog dialog = new ViewDishDialog(order);
        });
    }




    private void createTable(){
        ArrayList<Ingredient> ingredientList = IngredientFactory.getIngredients(dish.getDishId());
        Integer[] columns = new Integer[] {IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_DESCRIPTION }; // Columns can be changed
        tableModel = new IngredientTableModel(ingredientList, columns);
        ingredientTable = new JTable(tableModel);
        ingredientTable.setFillsViewportHeight(true);
    }

    public static void main(String[] args){
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new DishInfoView(DishFactory.getDish(1)));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        createTable();
        // TODO: place custom component creation code here
        // Fill table with dummy data

    }
}
