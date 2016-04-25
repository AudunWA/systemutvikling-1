package no.ntnu.iie.stud.cateringstorm.gui.dialogs;


import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;

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
    private JTextField employeeTypeField;
    private JPanel textPanel;
    private JLabel forenameLabel;
    private JLabel surnameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JComboBox etBox;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel employeeTypeLabel;
    private boolean addedNewValue;

    public AddEmployeeDialog() {
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);
        setLocationRelativeTo(getParent());

        addedNewValue = false;

        saveButton.addActionListener(new ActionListener() {
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

    public static void main(String[] args) {
        final int WIDTH = 1000;
        final int HEIGHT = 1000;
        AddEmployeeDialog dialog = new AddEmployeeDialog();
        dialog.pack();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(dialog.getParent());
        System.exit(0);
    }

    private void createComboBoxType() {
        Object[] status = {"Employee", "Chef", "Chauffeur", ""};

        etBox = new JComboBox(status);
        etBox.setSelectedIndex(0);
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
        int selectedIndex = etBox.getSelectedIndex();

        EmployeeType employeeType = EmployeeType.getEmployeeType(selectedIndex);

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a username.");
            return;
        }

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

        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a phone number.");
            return;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in an email.");
            return;
        }

        Employee employee = EmployeeFactory.createEmployee(username, password, surname, forename, address, phone, email, employeeType, EmployeeFactory.getSalaryByType(employeeType.getType()), EmployeeFactory.getCommissionByType(employeeType.getType()));
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
