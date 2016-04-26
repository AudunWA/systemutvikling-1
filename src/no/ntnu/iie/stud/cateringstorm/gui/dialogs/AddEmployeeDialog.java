package no.ntnu.iie.stud.cateringstorm.gui.dialogs;


import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.InputUtil;

import javax.swing.*;
import java.awt.event.*;

/**
 * GUI Dialog for adding an Employee to the database
 */

public class AddEmployeeDialog extends JDialog {
    private JPanel mainPanel;
    private JButton saveButton;
    private JButton cancelButton;
    private JTextField usernameField;
    private JTextField forenameField;
    private JTextField surnameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox<String> typeComboBox;
    private boolean addedNewValue;

    public AddEmployeeDialog() {
        setTitle("Add an employee");
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);
        setLocationRelativeTo(getParent());

        addedNewValue = false;
        addActionListeners();
        pack();
    }
    private void addActionListeners(){
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
        final int WIDTH = 400;
        final int HEIGHT = 500;
        AddEmployeeDialog dialog = new AddEmployeeDialog();
        dialog.pack();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(dialog.getParent());
        System.exit(0);
    }

    private void createComboBoxType() {
        String[] status = {"General employee", "Chef", "Chauffeur", "Nutrition Expert", "Administrator", "Salesperson"};

        typeComboBox = new JComboBox<>(status);
        typeComboBox.setSelectedIndex(0);
    }

    /**
     * Called when ok button is pressed
     * Creates a new Employee with attributes from user input
     */
    private void onOK() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String forename = forenameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        int selectedIndex = typeComboBox.getSelectedIndex();

        EmployeeType employeeType = EmployeeType.getEmployeeType(selectedIndex);

        if (username.isEmpty()) {
            Toast.makeText(this, "Please fill in a username.", Toast.Style.ERROR).display();
            return;
        }

        if (forename.isEmpty()) {
            Toast.makeText(this, "Please fill in a forename", Toast.Style.ERROR).display();
            return;
        }

        if (surname.isEmpty()) {
            Toast.makeText(this, "Please fill in a surname", Toast.Style.ERROR).display();
            return;
        }

        if (address.isEmpty()) {
            Toast.makeText(this, "Please fill in an address.", Toast.Style.ERROR).display();
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Please fill in a phone number.", Toast.Style.ERROR).display();
            return;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Please fill in an email.", Toast.Style.ERROR).display();
            return;
        }

        if(!InputUtil.isValidPhoneNumber(phone)) {
            Toast.makeText(this, "Invalid phone number.", Toast.Style.ERROR).display();
            return;
        }

        if(!InputUtil.isValidEmail(email)) {
            Toast.makeText(this, "Invalid email address.", Toast.Style.ERROR).display();
            return;
        }

        if(!InputUtil.isValidStreetAddress(address)) {
            Toast.makeText(this, "Invalid address.", Toast.Style.ERROR).display();
            return;
        }

        Employee employee = EmployeeFactory.createEmployee(username, password, forename,surname, address, phone, email, employeeType, EmployeeFactory.getSalaryByType(employeeType.getType()), EmployeeFactory.getCommissionByType(employeeType.getType()));
        addedNewValue = true;
        if (employee == null) {
            JOptionPane.showMessageDialog(this, "An error occurred, please try again.");
        } else {
            JOptionPane.showMessageDialog(this, employee);
        }

        dispose();
    }

    public boolean hasAddedNewValue() {
        return addedNewValue;
    }

    /**
     * Called when cancel button, escape or cross is pressed
     */
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        createComboBoxType();
    }
}
