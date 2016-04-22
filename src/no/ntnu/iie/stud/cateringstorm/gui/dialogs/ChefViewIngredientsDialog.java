package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChefViewIngredientsDialog extends JDialog {
    private JPanel mainPanel;
    private JButton okButton;
    private JTable ingredientTable;
    private JScrollPane ingredientPane;
    private JLabel ingredientLabel;
    private JButton cancelButton;

    private IngredientTableModel tableModel;
    private Dish dish;
    private ArrayList<Ingredient> ingredientList;

    public ChefViewIngredientsDialog(Dish dish) {
        this.dish = dish;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        ingredientLabel.setText("Ingredients for: " + dish.getName());


        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        ChefViewIngredientsDialog dialog = new ChefViewIngredientsDialog(DishFactory.getDish(2));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onCancel() {
        dispose();
    }

    private void createTable() {

        ingredientList = IngredientFactory.getIngredients(dish.getDishId());
        Integer[] columns = new Integer[]{IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_DESCRIPTION};
        tableModel = new IngredientTableModel(ingredientList, columns);
        ingredientTable = new JTable(tableModel);

        ingredientTable.getTableHeader().setReorderingAllowed(false);
        ingredientPane = new JScrollPane(ingredientTable);
        ingredientTable.setFillsViewportHeight(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
    }
}
