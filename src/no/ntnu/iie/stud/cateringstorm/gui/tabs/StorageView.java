package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.gui.backend.IngredientTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddIngredientDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tableModels.ChauffeurOrderTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public StorageView() {
        add(mainPanel);

        addIngredientButton.addActionListener(e -> {
            AddIngredientDialog dialog = new AddIngredientDialog();
            dialog.pack();
            dialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StorageView");
        frame.setContentPane(new StorageView().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        createTable();
    }

    private void createTable(){
        ArrayList<Ingredient> ingredients = IngredientFactory.viewAllIngredient();
        Integer[] columns = new Integer[] { IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_ID, IngredientTableModel.COLUMN_EXPIRE_DATE };

        IngredientTableModel tableModel = new IngredientTableModel(ingredients, columns);
        ingredientTable = new JTable(tableModel);
        ingredientTable.getTableHeader().setReorderingAllowed(false);
    }
}
