package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddIngredientDialog;

import javax.swing.*;
import java.util.ArrayList;

/**
 * GUI for displaying the inventory of ingredients to chefs and nutrition experts(?).
 * Lets the user add, edit and remove ingredients.
 * Created by Audun on 01.04.2016.
 */
public class StorageView extends JPanel {
    private JPanel mainPanel;
    private JTable ingredientTable;
    private JButton incrementSupply;
    private JTextField textField1;
    private JButton addIngredientButton; 
    private JTextField searchTextField;
    private JButton searchButton;
    private IngredientTableModel tableModel;

    public StorageView() {
        add(mainPanel);

        addIngredientButton.addActionListener(e -> {
            AddIngredientDialog dialog = new AddIngredientDialog();
            dialog.pack();
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            if(dialog.getAddedNewValue()) {
                // Refresh data
                tableModel.setRows(IngredientFactory.getAllIngredients());
            }
        });

        incrementSupply.addActionListener(e -> {
            int selectedRow = ingredientTable.getSelectedRow();
            if(selectedRow == -1) {
                return;
            }

            Ingredient ingredient = tableModel.getValue(selectedRow);
            ingredient.incrementAmount();
            int affectedRows = IngredientFactory.updateIngredientAmount(ingredient.getIngredientId(), ingredient.getAmount());

            if(affectedRows == 1) {
                tableModel.setRow(selectedRow, ingredient);
            } else {
                // TODO: Log error?
            }
        });

        searchButton.addActionListener(e -> {
            ArrayList<Ingredient> newRows;
            if(searchTextField.getText().trim().equals("")) {
                newRows = IngredientFactory.getAllIngredients();
            } else {
                newRows = IngredientFactory.getAllIngredientsByQuery(searchTextField.getText());
            }
            tableModel.setRows(newRows);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StorageView");
        frame.setContentPane(new StorageView().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        createTable();
    }

    private void createTable(){
        ArrayList<Ingredient> ingredients = IngredientFactory.getAllIngredients();
        Integer[] columns = new Integer[] { IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_ID, IngredientTableModel.COLUMN_EXPIRE_DATE, IngredientTableModel.COLUMN_AMOUNT };

        tableModel = new IngredientTableModel(ingredients, columns);
        ingredientTable = new JTable(tableModel);
        ingredientTable.getTableHeader().setReorderingAllowed(false);
    }
}
