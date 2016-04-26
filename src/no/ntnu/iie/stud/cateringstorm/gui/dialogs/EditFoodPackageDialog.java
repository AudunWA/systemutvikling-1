package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * GUI Dialog for editing an existing foodpackage in the database
 */

public class EditFoodPackageDialog extends JDialog {
    private static final Integer[] COLUMNS_AVAILABLE_DISHES = {DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION};

    // fixed
    private boolean addedNewValue;

    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField nameField;
    private JTextField costField;
    private JCheckBox activeCheckbox;
    private JButton addRemoveButton;
    private FoodPackage foodPackage;

    private JTable leftSideTable;
    private DishTableModel leftSideModel;

    private JTable rightSideTable;
    private DishTableModel rightSideModel;

    public EditFoodPackageDialog(FoodPackage foodPackage) {
        this.foodPackage = foodPackage;
        setTitle("Edit food package");
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        addActionListeners();

    }

    public static void main(String[] args) {
        final int height = 700;
        final int width = 600;
        EditFoodPackageDialog dialog = new EditFoodPackageDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setSize(width, height);
        System.exit(0);
    }

    private void addActionListeners() {
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
        addRemoveButton.addActionListener(e -> onAR());
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

    /**
     * Fills up the components with data from the food package.
     * Table data is not loaded here, but in createTables().
     */
    private void loadData() {
        nameField.setText(foodPackage.getName());
        costField.setText(((Double) foodPackage.getCost()).toString());
        activeCheckbox.setSelected(foodPackage.isActive());
    }

    private void createUIComponents() {
        createTables();
    }

    /**
     * Initializes and fills the tables with appropriate data
     */
    private void createTables() {
        // Available dishes (all active ones)
        rightSideModel = new DishTableModel(DishFactory.getActiveDishes(), COLUMNS_AVAILABLE_DISHES);
        rightSideTable = new JTable(rightSideModel);

        // Current dishes
        leftSideModel = new DishTableModel(DishFactory.getDishes(foodPackage.getFoodPackageId()), COLUMNS_AVAILABLE_DISHES);
        leftSideTable = new JTable(leftSideModel);
    }

    /**
     * Called when add/remove button is pressed (Between the tables)
     * Adds or removes selected dishes in the left side table
     */
    private void onAR() {
        // Check if both tables are selected (shouldn't really happen, but we check anyways)
        if (leftSideTable.getSelectedRow() > -1 && rightSideTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. Error.");
            leftSideTable.clearSelection();
            rightSideTable.clearSelection();
            return;
        }

        if (rightSideTable.getSelectedRow() > -1) {
            Dish rightSideSelectedDish = rightSideModel.getValue(rightSideTable.getSelectedRow());
            Dish existingLeftSideDish = null;

            ArrayList<Dish> dishes = leftSideModel.getRowsClone();
            for (Dish dish : dishes) {
                if (dish.getDishId() == rightSideSelectedDish.getDishId()) {
                    existingLeftSideDish = dish;
                    break;
                }
            }

            if (existingLeftSideDish == null) {
                // Add dish to left side
                leftSideModel.addRow(rightSideSelectedDish);
            } else {
                // Show error
                Toast.makeText(this, "Dish already added.", Toast.Style.ERROR).display();
                return;
            }
        } else if (leftSideTable.getSelectedRow() > -1) {
            Dish leftSideSelectedDish = leftSideModel.getValue(leftSideTable.getSelectedRow());

//            if(selectedRecurringOrder.getAmount() > 1) {
//                // Decrement
//                selectedRecurringOrder.decrementAmount();
//                selectedPackagesModel.updateRow(selectedPackagesTable.getSelectedRow());
//            } else {
            // Remove
            leftSideModel.removeRow(leftSideTable.getSelectedRow());
            leftSideTable.clearSelection();
//            }
        } else {
            Toast.makeText(this, "Select a row.", Toast.Style.ERROR).display();
        }
    }

    /**
     * Called when OK button is pressed.
     * Saves the changes to the existing foodpackage.
     */
    private void onOK() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if (dialogResult != JOptionPane.YES_OPTION) {
            return;
        }

        String name = nameField.getText();
        if (name.isEmpty()) {
            Toast.makeText(this, "Invalid name.", Toast.Style.ERROR).display();
            return;
        }

        double cost;
        try {
            cost = Double.parseDouble(costField.getText().replace(',', '.'));
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Invalid cost.").display();
            return;
        }

        if (cost < 0) {
            Toast.makeText(this, "Invalid cost (negative).", Toast.Style.ERROR).display();
            return;
        }

        boolean active = activeCheckbox.isSelected();

        foodPackage.setName(name);
        foodPackage.setCost(cost);
        foodPackage.setActive(active);

        // Do the actual update in database
        if (!FoodPackageFactory.updateFoodPackage(foodPackage, leftSideModel.getRowsClone())) {
            JOptionPane.showMessageDialog(this, "Dish was not updated, please try again later.");
        } else {
            addedNewValue = true;
        }
        dispose();
    }

    public boolean getAddedNewValue() {
        return addedNewValue;
    }

    /**
     * Called when cancel button, escape or the cross is pressed
     */
    private void onCancel() {
        dispose();
    }
}