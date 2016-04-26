package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GUI Dialog for adding a foodpackage to the database
 */

public class AddFoodPackageDialog extends JDialog {
    private JPanel mainPanel;
    private JButton addButton;
    private JButton cancelButton;
    private JCheckBox isActive;
    private JTextField foodPackageNameTextField;
    private JTextField costTextField;
    private JLabel foodPackageName;
    private JLabel foodPackageCost;
    private JButton addRemoveButton;
    private JTable dishTable;
    private JTable addedTable;

    private ArrayList<Dish> addedList;
    private ArrayList<FoodPackage> foodPackages = new ArrayList<>();
    private ArrayList<Dish> dishList;


    public AddFoodPackageDialog() {
        setTitle("Add a food package");
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(addButton);
        loadData();

        addButton.addActionListener(e -> onOk());
        cancelButton.addActionListener(e -> onCancel());
        addRemoveButton.addActionListener(e -> onAR());

        dishTable.getSelectionModel().addListSelectionListener(e -> {

        });

        addedTable.getSelectionModel().addListSelectionListener(e -> {

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
        AddFoodPackageDialog dialog = new AddFoodPackageDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Called when add/remove button is pressed
     */
    private void onAR() {
        if (dishTable.getSelectedRow() > -1 && addedTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. please deselect one by pressing with crtl");
            dishTable.clearSelection();
            addedTable.clearSelection();
        } else {
            if (dishTable.getSelectedRow() > -1) {
                boolean check = true;
                for (Dish dishes : addedList) {
                    if (dishes.getDishId() == (dishTable.getSelectedRow() + 1)) {
                        check = false;
                    }
                }
                if (check) {
                    addedList.add(dishList.get(dishTable.getSelectedRow()));
                    ((EntityTableModel) addedTable.getModel()).setRows(addedList);
                    dishTable.clearSelection();
                } else {
                    JOptionPane.showMessageDialog(this, "This order is already added.");
                    dishTable.clearSelection();
                }
            } else if (addedTable.getSelectedRow() > -1 && !dishTable.isColumnSelected(1) && !dishTable.isColumnSelected(2)) {
                addedList.remove(addedTable.getSelectedRow());
                ((EntityTableModel) addedTable.getModel()).setRows(addedList);
                addedTable.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Please unselect the package list. Do this by clicking with ctrl down.");
                addedTable.clearSelection();
            }
        }
    }

    /**
     * Called when ok button is pressed
     * Creates a new FoodPakcage with attributes from user input
     */
    private void onOk() {
        String name = foodPackageNameTextField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a name.");
            return;
        }
        double cost;
        try {
            cost = Double.parseDouble(costTextField.getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please fill in a valid amount (use . as comma).");
            return;
        }
        if (cost < 0) {
            JOptionPane.showMessageDialog(this, "Please fill in a valid amount (Not negative)");
            return;
        }
        boolean active = isActive.isSelected();

        ArrayList<Integer> test = new ArrayList<>();
        test.add(dishTable.getSelectedRow() + 1);

        foodPackages.add(new FoodPackage(0, name, cost, active));

        int check = FoodPackageFactory.getAllFoodPackages().size();

        ArrayList<Dish> dishes = new ArrayList<>();
        for (int k = 0; k < addedList.size(); k++) {
            dishes.add(addedList.get(k));
        }

        if (dishes.size() < 1) {
            JOptionPane.showMessageDialog(this, "Please add dish(s))");
            return;
        }

        FoodPackage foodPackage = FoodPackageFactory.createFoodPackage(name, cost, dishes);

        if (FoodPackageFactory.getAllFoodPackages().size() > check) {
            JOptionPane.showMessageDialog(this, "Add successful");
            addedList = new ArrayList<>();
            ((EntityTableModel) addedTable.getModel()).setRows(addedList);
        }
        JOptionPane.showMessageDialog(this, foodPackage);
    }

    /**
     * Called when cancel button, escape or cross is pressed
     */
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void loadData() {
        isActive.setSelected(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here


        dishList = DishFactory.getAllDishes();
        Integer[] columns = new Integer[]{DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION};
        DishTableModel table = new DishTableModel(dishList, columns);
        dishTable = new JTable(table);
        dishTable.getTableHeader().setReorderingAllowed(false);

        addedList = new ArrayList<>();
        Integer[] addedColumns = new Integer[]{DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION};
        DishTableModel addedObjects = new DishTableModel(addedList, columns);
        addedTable = new JTable(addedObjects);
        dishTable.getTableHeader().setReorderingAllowed(false);
    }
}
