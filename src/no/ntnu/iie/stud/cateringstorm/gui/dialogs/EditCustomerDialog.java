package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.CustomerTableModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
    private JLabel infoLabel;
    private JTextField inputField;
    private JPanel buttonPanel;
    private JButton saveButton;
    private JButton cancelButton;
    private CustomerTableModel model;

    public EditCustomerDialog(Customer customer) {
        this.customer = customer;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);//Consider not using this for safety reasons
        setLocationRelativeTo(getParent());

        saveButton.addActionListener(e -> {
            onOK();
        });
        cancelButton.addActionListener(e -> {
            onCancel();
        });
        choiceBox.addActionListener(e -> {
            setTextField();
        });
        inputField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (inputField.isEnabled()) {
                    emptyTextField(inputField.getText());
                }
            }
        });

    }

    //Test method
    public static void main(String[] args) {
        final int WIDTH = 300;
        final int HEIGHT = 200;
        EditCustomerDialog dialog = new EditCustomerDialog(null);
        dialog.pack();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);
        System.exit(0);
    }
    /**
     * Called when ok button is pressed
     * Updates and saves the changes to the existing Customer
     */
    private void onOK() {
        final int COLUMN_SURNAME = 0;
        final int COLUMN_FORENAME = 1;
        final int COLUMN_ADDRESS = 2;
        final int COLUMN_PHONE = 3;
        final int COLUMN_EMAIL = 4;
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);

        if (dialogResult == 0) {
            String input = inputField.getText();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value");
                return;
            }
            switch (getChoice()) {

                case COLUMN_FORENAME:
                    customer.setForename(input);
                    break;
                case COLUMN_SURNAME:
                    customer.setSurname(input);
                    break;
                case COLUMN_ADDRESS:
                    customer.setAddress(input);
                    break;
                case COLUMN_PHONE:
                    customer.setPhone(input);
                    break;
                case COLUMN_EMAIL:
                    customer.setEmail(input);

                default:
                    return;
            }
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

    private void setTextField() {
        final int COLUMN_SURNAME = 0;
        final int COLUMN_FORENAME = 1;
        final int COLUMN_ADDRESS = 2;
        final int COLUMN_PHONE = 3;
        final int COLUMN_EMAIL = 4;
        switch (getChoice()) {

            case COLUMN_FORENAME:
                inputField.setText("Enter new forename");
                inputField.setEnabled(true);
                break;
            case COLUMN_SURNAME:
                inputField.setText("Enter new surname");
                inputField.setEnabled(true);
                break;
            case COLUMN_ADDRESS:
                inputField.setText("Enter new address");
                inputField.setEnabled(true);
                break;
            case COLUMN_PHONE:
                inputField.setText("Enter new phone number");
                inputField.setEnabled(true);
                break;
            case COLUMN_EMAIL:
                inputField.setText("Enter new email");
                inputField.setEnabled(true);
                break;
            default:
                inputField.setText("Please choose a value in combobox below");
        }
    }

    private int getChoice() {
        return choiceBox.getSelectedIndex();
    }

    private void emptyTextField(String text) {
        if (inputField.getText().equals(text)) {
            inputField.setText("");
        }
    }

    private void createComboBox() {
        ArrayList<Customer> customerList = CustomerFactory.getAllCustomers();
        Integer[] columns = new Integer[]{CustomerTableModel.COLUMN_SURNAME, CustomerTableModel.COLUMN_FORENAME, CustomerTableModel.COLUMN_ADDRESS, CustomerTableModel.COLUMN_PHONE, CustomerTableModel.COLUMN_EMAIL}; // Columns can be changed
        model = new CustomerTableModel(customerList, columns);
        Object[] choices = new Object[model.getColumnCount()];

        //Loop to select desired indexes from table model.
        int ctr = 0;
        int unwantedColumn1 = 6, unwantedColumn2 = 7;
        for (int i = 1; i < 8; i++) {
            if (i != unwantedColumn1 && i != unwantedColumn2) {
                choices[ctr] = model.getColumnName(i);
                ctr++;
            }
        }
        choiceBox = new JComboBox(choices);
        choiceBox.setSelectedIndex(0);
    }

    private void createTextField() {
        inputField = new JTextField(20);
        inputField.setText("Choose a value in combobox below");
        inputField.setEnabled(false);
        add(inputField);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createComboBox();
        createTextField();
    }

    public boolean getAddedNewValue() {
        return addedNewValue;
    }
}
