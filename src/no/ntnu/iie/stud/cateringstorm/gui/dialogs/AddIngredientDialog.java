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

public class AddIngredientDialog extends JDialog {
    private boolean addedNewValue;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField amountTextField;
    private JCheckBox vegetarianCheckBox;
    private JTextField unitTextField;
    private JDatePickerImpl arrivalDatePicker;
    private JDatePickerImpl expireDatePicker;

    public AddIngredientDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
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
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please fill in a valid amount (use . as comma).");
            return;
        }

        String unit = unitTextField.getText();
        if (unit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in an unit.");
            return;
        }

        Boolean vegetarian = vegetarianCheckBox.isSelected();
        java.util.Date arrivalDate = (java.util.Date) arrivalDatePicker.getModel().getValue();
        if(arrivalDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill in an arrival date.");
            return;
        }

        java.util.Date expireDate = (java.util.Date) expireDatePicker.getModel().getValue();
        if(expireDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill in an expire date.");
            return;
        }

        if (arrivalDate.after(expireDate)) {
            JOptionPane.showMessageDialog(this, "Arrival date can't be after expire date!");
            return;
        }

        // Insert the ingredient
        Timestamp arrivalTime = new Timestamp(arrivalDate.getTime());
        Date expireDateSql = new Date(expireDate.getTime());

        Ingredient ingredient = IngredientFactory.insertNewIngredient(name, description, amount, unit, vegetarian, arrivalTime, expireDateSql);
        if (ingredient == null) {
            JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
        } else {
            addedNewValue = true;

            // Debug code
            System.out.println(ingredient);
        }
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AddIngredientDialog dialog = new AddIngredientDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void createUIComponents() {
        // Create date pickers
        UtilDateModel model = new UtilDateModel();

        // Dunno
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
