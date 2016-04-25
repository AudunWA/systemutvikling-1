package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDish;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDishFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientDishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;

import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * GUI Dialog for editing an existing dish in the database
 */

public class EditDishDialog extends JDialog {
    private boolean addedNewValue;

    private static final Integer[] COLUMNS_AVAILABLE_INGREDIENTS = {IngredientDishTableModel.COLUMN_DISH_NAME, IngredientDishTableModel.COLUMN_INGREDIENT_ID, IngredientDishTableModel.COLUMN_INGREDIENT_NAME, IngredientDishTableModel.COLUMN_QUANTITY, IngredientDishTableModel.COLUMN_UNIT};


    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField descriptionField;
    private JTextField nameField;
    private JComboBox<String> typeComboBox;
    private JCheckBox statusCheckBox;
    private JButton swapButton;


    private JTable addedIngredientTable;
    private IngredientDishTableModel leftSideModel;

    private JTable rightSideTable;
    private JSpinner addRemoveSpinner;
    private IngredientTableModel rightTableModel;

    ArrayList<IngredientDish> addedList;
    ArrayList<Ingredient> selectionList;

    private Dish dish;

    public boolean getAddedNewValue() {
        return addedNewValue;
    }

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

    public static void main(String[] args) {
        EditDishDialog dialog = new EditDishDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createComboBoxType();
        createTables();
    }

    private void createComboBoxType() {
        String[] status = {"Appetizer", "Main course", "Dessert"};

        typeComboBox = new JComboBox<>(status);
        typeComboBox.setSelectedIndex(0);
    }

    private void createTables() {
        // Available dishes (all active ones)

        selectionList = IngredientFactory.getAllIngredients();
        rightTableModel = new IngredientTableModel(selectionList, COLUMNS_AVAILABLE_INGREDIENTS);
        rightSideTable = new JTable(rightTableModel);
        rightSideTable.getTableHeader().setReorderingAllowed(false);

        // Current dishes
        addedList = IngredientDishFactory.getAllIngredientsDishesInOrder(dish.getDishId());
        leftSideModel = new IngredientDishTableModel(addedList, COLUMNS_AVAILABLE_INGREDIENTS);
        addedIngredientTable = new JTable(leftSideModel);
        addedIngredientTable.getTableHeader().setReorderingAllowed(false);
    }

    private void onOK() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if (dialogResult == 0) {

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

        if ((Integer)addRemoveSpinner.getValue() < 1) {
            JOptionPane.showMessageDialog(this, "Please set a positive amount on the spinner");
            return;
            // Check if both tables are selected (shouldn't really happen, but we check anyways)
        }
        if (addedIngredientTable.getSelectedRow() > -1 && rightSideTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. Error.");
            addedIngredientTable.clearSelection();
            rightSideTable.clearSelection();
            return;
        }

        if (addedIngredientTable.getSelectedRow() > -1) {
            if (addedList.get(addedIngredientTable.getSelectedRow()).getQuantity() < (Integer) addRemoveSpinner.getValue() + 1) {
                addedList.remove(addedIngredientTable.getSelectedRow());
            } else {
                addedList.get(addedIngredientTable.getSelectedRow()).setQuantity(addedList.get(addedIngredientTable.getSelectedRow()).getQuantity() - (Integer) addRemoveSpinner.getValue());
            }
        }

        boolean check = true;

        if (rightSideTable.getSelectedRow() > -1) {
            IngredientDish ingDish = new IngredientDish(IngredientFactory.getIngredient(selectionList.get(rightSideTable.getSelectedRow()).getIngredientId()),null,(Integer)addRemoveSpinner.getValue(),selectionList.get(rightSideTable.getSelectedRow()).getUnit());

            for (int i = 0; i <addedList.size(); i++){
                if (addedList.get(i).getIngredient().getIngredientId() == ingDish.getIngredient().getIngredientId()){
                    addedList.get(i).setQuantity(addedList.get(i).getQuantity() + ingDish.getQuantity());
                    check = false;
                }
            }

            if (check){
                addedList.add(ingDish);
            }
        }
        leftSideModel.setRows(addedList);
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }


}
