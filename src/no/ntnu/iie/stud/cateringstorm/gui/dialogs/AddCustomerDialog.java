package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.util.InputUtil;

import javax.swing.*;
import java.awt.event.*;

/**
 * GUI Dialog for adding a customer to the database
 */
public class AddCustomerDialog extends JDialog {
    private JPanel mainPanel;
    private JTextField forenameField;
    private JButton saveButton;
    private JTextField surnameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JButton cancelButton;
    private JTextField emailField;
    private boolean addedNewValue;

    public AddCustomerDialog() {
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);// Consider not using this for safety reasons
        setLocationRelativeTo(getParent());

        addedNewValue = false;
        saveButton.addActionListener(e -> onOK());

        cancelButton.addActionListener(e -> onCancel());

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
        final int WIDTH = 320;
        final int HEIGHT = 400;
        AddCustomerDialog dialog = new AddCustomerDialog();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(dialog.getParent());
        System.exit(0);

    }

    /**
     * Called when Ok Button is pressed
     * Creates a new customer with the added attributes
     */
    private void onOK() {
        String forename = forenameField.getText().trim();
        String surname = surnameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (forename.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a forename");
            return;
        }
        if (surname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a surname");
            return;
        }
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in an address.");
            return;
        }

        if (phone.isEmpty() || !InputUtil.isValidPhoneNumber(phone)) {
            JOptionPane.showMessageDialog(this, "Please fill in a phone number.");
            return;
        }
        if (email.isEmpty() || !InputUtil.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please fill in an email.");
            return;
        }
        Customer customer = CustomerFactory.createCustomer(forename, surname, address, true, phone, email);

        if (customer == null) {
            JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
        } else {
            addedNewValue = true;
        }
        dispose();
    }

    public boolean hasAddedNewValue() {
        return addedNewValue;
    }

    /**
     * Called on cancel button, escape or the exit cross in the top right corner is pressed
     */
    private void onCancel() {
        dispose();
    }
}
