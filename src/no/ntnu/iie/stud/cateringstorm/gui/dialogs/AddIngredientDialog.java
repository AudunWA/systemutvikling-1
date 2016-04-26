package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.gui.util.SimpleDateFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * GUI Dialog for adding an ingredient to the database
 */

public class AddIngredientDialog extends JDialog {
    private boolean addedNewValue;

    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField amountTextField;
    private JCheckBox vegetarianCheckBox;
    private JTextField unitTextField;
    private JDatePickerImpl arrivalDatePicker;
    private JDatePickerImpl expireDatePicker;

    public AddIngredientDialog() {
        setTitle("Add an ingredient");
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());

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
        AddIngredientDialog dialog = new AddIngredientDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Called when ok button is pressed
     * Creates a new Ingredient with attributes from user inputted data
     */
    private void onOK() {
        // Check that values are valid
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a name.");
            return;
        }

        String description = descriptionTextField.getText();
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a description.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountTextField.getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please fill in a valid amount (use . as comma).");
            return;
        }
        if (amount < 0) {
            JOptionPane.showMessageDialog(this, "Please fill in a valid amount (Not negative)");
            return;
        }

        String unit = unitTextField.getText();
        if (unit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in an unit.");
            return;
        }

        Boolean vegetarian = vegetarianCheckBox.isSelected();
        java.util.Date arrivalDate = new Date(System.currentTimeMillis());
        if (arrivalDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill in an arrival date.");
            return;
        }

        java.util.Date expireDate = (java.util.Date) expireDatePicker.getModel().getValue();
        if (expireDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill in an expire date.");
            return;
        }

        if (expireDate.before(arrivalDate)) {
            JOptionPane.showMessageDialog(this, "Expire date can't be before arrival!");
            return;
        }

        // Insert the ingredient
        Timestamp arrivalTime = new Timestamp(arrivalDate.getTime());
        Date expireDateSql = new Date(expireDate.getTime());
        /*Timestamp arrivalDate, String name, String description, boolean vegetarian, Date expireDate, double amount,String unit*/
        Ingredient ingredient = IngredientFactory.createIngredient(arrivalTime, name, description, vegetarian, expireDateSql, amount, unit);
        if (ingredient == null) {
            JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
        } else {
            addedNewValue = true;

            // Debug code
            System.out.println(ingredient);
        }
        dispose();
    }

    /**
     * Called when cancel button, escape or the cross is pressed
     */
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public void createUIComponents() {
        // Create date pickers
        UtilDateModel model = new UtilDateModel();

        // Dunn
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        arrivalDatePicker = new JDatePickerImpl(datePanel, new SimpleDateFormatter());

        model = new UtilDateModel();
        datePanel = new JDatePanelImpl(model, p);
        expireDatePicker = new JDatePickerImpl(datePanel, new SimpleDateFormatter());
    }

    public boolean getAddedNewValue() {
        return addedNewValue;
    }
}
