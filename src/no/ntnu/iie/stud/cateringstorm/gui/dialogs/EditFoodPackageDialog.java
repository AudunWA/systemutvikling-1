package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditFoodPackageDialog extends JDialog {

    // fixed
    private boolean addedNewValue;

    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField nameTextField;
    private JTextField costTextField;
    private JCheckBox isActive;
    private JTable addedTable;
    private JTable dishTable;
    private JButton swapButton;
    private FoodPackage foodPackage;
    private ArrayList<Dish> addedList;
    private ArrayList<FoodPackage> foodPackages = new ArrayList<>();
    private ArrayList<Dish> dishList;

    public EditFoodPackageDialog(FoodPackage foodPackage) {
        this.foodPackage = foodPackage;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        swapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onSwap(); }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

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
    private void onSwap() {
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

    private void onOK() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if (dialogResult == 0) {

            String name = nameTextField.getText();
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

            ArrayList<Dish> dishes = new ArrayList<>();
            for (int k = 0; k < addedList.size(); k++) {
                dishes.add(addedList.get(k));
            }

            if (dishes.size() < 1) {
                JOptionPane.showMessageDialog(this, "Please add package(s)");
                return;
            }

            foodPackage.setName(name);
            foodPackage.setCost(cost);
            foodPackage.setActive(active);

            if (FoodPackageFactory.updateFoodPackage(foodPackage) != 1) {
                JOptionPane.showMessageDialog(this, "Dish was not update, please try again later.");
            }
            if (foodPackage == null) {
                JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
            } else {
                // Debug code
                JOptionPane.showMessageDialog(this, foodPackage);
                addedNewValue = true;
            }
            dispose();
        }
    }

    public boolean getAddedNewValue() {
        return addedNewValue;
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
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

}