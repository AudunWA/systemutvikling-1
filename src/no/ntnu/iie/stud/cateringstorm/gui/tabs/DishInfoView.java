package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GUI window for chefs, viewing detailed information about ingredients in a dish
 */
public class DishInfoView extends JFrame {
    private JButton editRowButton;
    private JPanel mainPanel;
    private JButton addRowButton;
    private JTable ingredientTable;
    private JButton saveButton;
    private JButton closeButton;

    private IngredientTableModel tableModel;
    private Dish dish;

    public DishInfoView(Dish dish) {
        this.dish = dish;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        addActionListeners();
    }

    private void addActionListeners(){
        closeButton.addActionListener(e -> onCancel());
       // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;

        DishInfoView dialog = new DishInfoView(null);// Must generate a Dish from Factory to run
        dialog.pack();
        dialog.setVisible(true);
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setLocationRelativeTo(null);//Puts window in middle of screen
    }

    private void onCancel() {
        dispose();
    }

    private void createTable() {
        ArrayList<Ingredient> ingredientList = IngredientFactory.getIngredients(dish.getDishId());
        Integer[] columns = new Integer[]{IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_DESCRIPTION}; // Columns can be changed
        tableModel = new IngredientTableModel(ingredientList, columns);
        ingredientTable = new JTable(tableModel);
        ingredientTable.setFillsViewportHeight(true);
    }

    private void createUIComponents() {
        createTable();
    }
}
