package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDish;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;


import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditDishDialog extends JDialog {
    private boolean addedNewValue;
    private static final Integer[] COLUMNS_AVAILABLE_INGREDIENTS = { IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_DESCRIPTION };

    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField descriptionField;
    private JTextField nameField;
    private JComboBox<String> typeComboBox;
    private JCheckBox statusCheckBox;
    private JButton swapButton;
    private Dish dish;

    private JTable leftSideTable;
    private IngredientTableModel leftSideModel;

    private JTable rightSideTable;
    private JSpinner addRemoveSpinner;
    private IngredientTableModel rightTableModel;


    public EditDishDialog(Dish dish) {
        this.dish = dish;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(e -> onOK());

        cancelButton.addActionListener(e -> onCancel());

        swapButton.addActionListener((e1 -> onSwap()));

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        loadData();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createComboBoxType();
        createTables();
    }

    private void createComboBoxType(){
        String[] status = {"Appetizer","Main course","Dessert"};

        typeComboBox = new JComboBox<>(status);
        typeComboBox.setSelectedIndex(0);
    }


    private static final Integer[] COLUMNS_AVAILABE_DISHES = { DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION };

    private void createTables() {
        // Available dishes (all active ones)
        rightTableModel = new IngredientTableModel(IngredientFactory.getAllIngredients(), COLUMNS_AVAILABLE_INGREDIENTS);
        rightSideTable = new JTable(rightTableModel);

        // Current dishes
        leftSideModel = new IngredientTableModel(IngredientFactory.getIngredients(dish.getDishId()), COLUMNS_AVAILABLE_INGREDIENTS);
        leftSideTable = new JTable(leftSideModel);
    }

    private void onOK() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if(dialogResult == 0) {

            String name = nameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in a name.");
                return;
            }

            String description = descriptionField.getText();
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in a description.");
                return;
            }

            int type = typeComboBox.getSelectedIndex() + 1;

            boolean isActive = statusCheckBox.isSelected();

            dish.setName(name);
            dish.setDescription(description);
            dish.setDishType(type);
            dish.setActive(isActive);

            DishFactory.updateDish(dish);
            if (DishFactory.updateDish(dish) != 1) {
                JOptionPane.showMessageDialog(this, "Dish was not update, please try again later.");
            }


            if (dish == null) {
                JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
            } else {
                // Debug code
                JOptionPane.showMessageDialog(this, dish);
                addedNewValue = true;
            }

            onCancel();
        }
    }

    private void loadData() {
        nameField.setText(dish.getName());
        descriptionField.setText(dish.getDescription());
        typeComboBox.setSelectedIndex(dish.getDishType());
        statusCheckBox.setSelected(dish.isActive());

    }
    private void onSwap() {
        if ((Integer) addRemoveSpinner.getValue() < 1) {
            JOptionPane.showMessageDialog(this, "Please set a positive amount on the spinner");
            return;
            // Check if both tables are selected (shouldn't really happen, but we check anyways)
        }
        if (leftSideTable.getSelectedRow() > -1 && rightSideTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. Error.");
            leftSideTable.clearSelection();
            rightSideTable.clearSelection();
            return;
        }
        if (rightSideTable.getSelectedRow() > -1) {
            Ingredient rightSideSelectedIngredient = rightTableModel.getValue(rightSideTable.getSelectedRow());
            Ingredient existingLeftSideIngredient = null;
            ArrayList<Ingredient> ingredients = leftSideModel.getRowsClone();
            for (Ingredient ingredient : ingredients) {
                if (dish.getDishId() == rightSideSelectedIngredient.getIngredientId()) {
                    existingLeftSideIngredient = ingredient;
                    break;
                }
            }

            if (existingLeftSideIngredient == null) {
                // Add ingredient to left side
                leftSideModel.addRow(rightSideSelectedIngredient);
            } else {
                // Show error
                Toast.makeText(this, "Ingredient already added.", Toast.Style.ERROR).display();
                return;
            }
        } else if (leftSideTable.getSelectedRow() > -1) {
            Ingredient leftSelectedIngredient = leftSideModel.getValue(leftSideTable.getSelectedRow());

            leftSideModel.removeRow(leftSideTable.getSelectedRow());
            leftSideTable.clearSelection();

        } else {
            Toast.makeText(this, "Select a row.", Toast.Style.ERROR).display();
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public boolean getAddedNewValue() {
        return addedNewValue;
    }

    public static void main(String[] args) {
        EditDishDialog dialog = new EditDishDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
