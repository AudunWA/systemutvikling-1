package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.CustomerTableModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GUI Dialog for editing an existing customer in the database
 */

public class EditCustomerDialog extends JDialog {
    private boolean addedNewValue;
    private Customer customer;
    private JPanel mainPanel;
    private JPanel textPanel;
    private JPanel cbPanel;
    private JComboBox choiceBox;
    private JTextField forenameField;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField surnameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField emailField;
    private JLabel forenameLabel;
    private JLabel surnameLabel;
    private JLabel addressLabel;
    private CustomerTableModel model;

    public EditCustomerDialog(Customer customer) {
        this.customer = customer;
        setTitle("Edit customer");
        setTextFields();
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);//Consider not using this for safety reasons
        setLocationRelativeTo(getParent());
        addActionListeners();

    }
    private void addActionListeners(){
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
        forenameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (forenameField.isEnabled()) {
                    emptyTextField(forenameField.getText(),forenameField);
                }
            }
        });
        surnameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (surnameField.isEnabled()) {
                    emptyTextField(surnameField.getText(),surnameField);
                }
            }
        });
        addressField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (forenameField.isEnabled()) {
                    emptyTextField(addressField.getText(),addressField);
                }
            }
        });
        phoneField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (forenameField.isEnabled()) {
                    emptyTextField(phoneField.getText(),phoneField);
                }
            }
        });
        emailField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (forenameField.isEnabled()) {
                    emptyTextField(emailField.getText(),emailField);
                }
            }
        });
    }
    //Test method
    public static void main(String[] args) {
        final int WIDTH = 300;
        final int HEIGHT = 400;
        EditCustomerDialog dialog = new EditCustomerDialog(CustomerFactory.getCustomer(1));
        dialog.pack();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);
        System.exit(0);
    }
    /**
     * Called when ok button is pressed.
     * Updates and saves the changes to the existing Customer.
     */
    private void onOK() {
        String forename = forenameField.getText(),
        surname = surnameField.getText(),
        address = addressField.getText(),
        phone = phoneField.getText(),
        email = emailField.getText();

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);

        if (dialogResult == 0) {
            String input = forenameField.getText();
            if (forename.isEmpty() || surname.isEmpty() || address.isEmpty()|| phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value in all text fields");
                return;
            }
                    customer.setForename(forename);
                    customer.setSurname(input);
                    customer.setAddress(input);
                    customer.setPhone(input);
                    customer.setEmail(input);

            int updatedId = CustomerFactory.updateCustomer(customer);
            if (updatedId != 1) {
                JOptionPane.showMessageDialog(this, "Customer wasn't updated, please try again later!");
            }
            if (customer == null) {
                JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
            } else {
                // Debug code
                JOptionPane.showMessageDialog(this, customer);
                addedNewValue = true;
            }
        }
        dispose();
    }
    /**
     * Called when cancel button, escape or the cross is pressed
     */
    private void onCancel() {
        dispose();
    }


    private void emptyTextField(String text,JTextField textField) {
        if (textField.getText().equals(text)) {
            textField.setText("");
        }
    }

    private void setTextFields(){
        forenameField.setText(customer.getForename());
        surnameField.setText(customer.getSurname());
        addressField.setText(customer.getAddress());
        phoneField.setText(customer.getPhone());
        emailField.setText(customer.getEmail());
    }


    public boolean getAddedNewValue() {
        return addedNewValue;
    }
}
