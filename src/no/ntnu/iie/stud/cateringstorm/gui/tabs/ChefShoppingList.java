package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDishFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GUI for extracting and printing a shopping list over low supplies for the Chef
 */
public class ChefShoppingList extends JPanel {
    private JPanel contentPane;
    private JButton goShoppingButton;
    private JButton refreshButton;
    private JTable ingredientTable;
    private JScrollPane ingredientPane;

    private ArrayList<Ingredient> ingredientList;
    private IngredientTableModel tableModel;

    public ChefShoppingList() {
        setLayout(new BorderLayout());
        add(contentPane, BorderLayout.CENTER);

        goShoppingButton.addActionListener(e -> {
            goShopping();
        });

        refreshButton.addActionListener(e -> {
            refresh();
        });

    }

    private void refresh(){
        ingredientList = IngredientFactory.getAllIngredients();
        tableModel.setRows(ingredientList);
    }

    private void goShopping(){
        int selectedRow = ingredientTable.getSelectedRow();
        if(selectedRow > -1) {
            Ingredient ingredient = ingredientList.get(selectedRow);
            ingredient.setAmount(1000.0);
            IngredientFactory.setAmountGivenIngredientId(ingredient.getIngredientId(), 1000.0);
            tableModel.setRows(ingredientList);
        }
    }

    public static void main(String[] args) {
        ChefShoppingList dialog = new ChefShoppingList();
        dialog.setVisible(true);
        System.exit(0);
    }

    private JTable getNewRenderedTable() {
        ingredientTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                double amount = (Double)tableModel.getValueAt(row, 2);

                if (amount < 100) {
                    setBackground(new Color(200, 100, 100));
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
        return ingredientTable;
    }

    private void createTable(){
        ingredientList = IngredientFactory.getAllIngredients();

        Integer[] columns = new Integer[]{IngredientTableModel.COLUMN_ID, IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_AMOUNT, IngredientTableModel.COLUMN_UNIT};
        tableModel = new IngredientTableModel(ingredientList, columns);
        ingredientTable = new JTable(tableModel);
        getNewRenderedTable();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();

    }
}
