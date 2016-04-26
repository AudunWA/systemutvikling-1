package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.InputUtil;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI Dialog for editing an existing employee in the database
 */


public class EditEmployeeDialog extends JDialog {
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

    private Employee employee;
    private boolean addedNewValue;
    private static final String PASSWORD_DUMMY = "dummyPassword";

    public EditEmployeeDialog(Employee employee) {
        this.employee = employee;
        setTitle("Edit an employee");
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);
        setLocationRelativeTo(getParent());

        addActionListeners();
        loadData();
        pack();
    }
    private void addActionListeners(){
        saveButton.addActionListener(e -> onSavePressed());
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
        final int WIDTH = 500;
        final int HEIGHT = 300;
        EditEmployeeDialog dialog = new EditEmployeeDialog(null);
        dialog.pack();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Called when save button is called
     * Updates and saves the changes to the existing Employee
     */
    private void onSavePressed() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);

        if (dialogResult == 0) {
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

            employee.setForename(forename);
            employee.setSurname(surname);
            employee.setPhoneNumber(phone);
            employee.setEmail(email);
            employee.setUsername(username);
            employee.setAddress(address);

            if(employee.getEmployeeType() != EmployeeType.ADMINISTRATOR) {
                // Not admin, can change type
                employee.setEmployeeType(employeeType);
            }
            if(!passwordField.getText().equals(PASSWORD_DUMMY)) {
                // Changed password
                // TODO: Update password
            }

            int updatedId = EmployeeFactory.updateEmployee(employee);
            if (updatedId != 1) {
                JOptionPane.showMessageDialog(this, "Employee was not updated. Please try again.");
            }
            if (employee == null) {
                JOptionPane.showMessageDialog(this, "An error occurred. Please try again.");
            } else {
                JOptionPane.showMessageDialog(this, employee);
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

    private void loadData() {
        forenameField.setText(employee.getForename());
        surnameField.setText(employee.getSurname());
        phoneField.setText(employee.getPhoneNumber());
        emailField.setText(employee.getEmail());
        usernameField.setText(employee.getUsername());
        passwordField.setText(PASSWORD_DUMMY);
        addressField.setText(employee.getAddress());
        typeComboBox.setSelectedIndex(employee.getEmployeeType().getType());

        // Disable role selection if user being edited is admin
        if(employee.getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            typeComboBox.setEnabled(false);
        }
    }

    private void createComboBoxType() {
        String[] status = {"General employee", "Chef", "Chauffeur", "Nutrition Expert", "Administrator", "Salesperson"};

        typeComboBox = new JComboBox<>(status);
        typeComboBox.setSelectedIndex(0);
    }

    public boolean getAddedNewValue() {
        return addedNewValue;
    }

    private void createUIComponents() {
        createComboBoxType();
    }
}
