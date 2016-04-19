package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;

import javax.swing.*;
import java.awt.event.*;

public class EditFoodPackageDialog extends JDialog {

    // fixed
    private boolean addedNewValue;

    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField nameTextField;
    private JTextField costTextField;
    private JCheckBox isActive;
    private FoodPackage foodPackage;

    public EditFoodPackageDialog(FoodPackage foodPackage) {
        this.foodPackage = foodPackage;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

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

    private void onOK() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if(dialogResult == 0) {

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
            } if (cost < 0) {
                JOptionPane.showMessageDialog(this, "Please fill in a valid amount (Not negative)");
                return;
            }


            boolean active = isActive.isSelected();

            foodPackage.setName(name);
            foodPackage.setCost(cost);
            foodPackage.setActive(active);

            FoodPackageFactory.updateFoodPackage(foodPackage);
            if (FoodPackageFactory.updateFoodPackage(foodPackage) != 1) {
                JOptionPane.showMessageDialog(this, "Dish wasn't updated, please try again later!");
            }


            if (foodPackage == null) {
                JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
            } else {
                // Debug code
                JOptionPane.showMessageDialog(this, foodPackage);
                addedNewValue = true;
            }

            onCancel();
        }
        dispose();
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
