package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EmployeeTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;


public class EditEmployeeDialog extends JDialog {
    private JPanel mainPanel;
    private JPanel textPanel;
    private JTextField inputField;
    private JPanel cbPanel;
    private JComboBox editEmployeeInfoCB;
    private JLabel infoLabel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel selectEmployeeType;
    private JCheckBox activeCheckBox;
    private JComboBox editEmployeeTypeCB;

    private Employee employee;
    private boolean addedNewValue;
    private EmployeeTableModel tableModel;

    public EditEmployeeDialog(Employee employee) {
        this.employee = employee;

        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        setLocationRelativeTo(getParent());

        okButton.addActionListener(e -> {
            onOK();
        });

        cancelButton.addActionListener(e -> {
            onCancel();
        });

        editEmployeeInfoCB.addActionListener(e -> {
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
        final int COLUMN_USERNAME = 0;
        final int COLUMN_FORENAME = 1;
        final int COLUMN_SURNAME = 2;
        final int COLUMN_ADDRESS = 3;
        final int COLUMN_PHONE = 4;
        final int COLUMN_EMAIL = 5;

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);

        if (dialogResult == 0) {
            String input = inputField.getText();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value.");
                return;
            }
            switch (getChoice()) {

                case COLUMN_USERNAME:
                    employee.setUsername(input);
                    break;
                case COLUMN_FORENAME:
                    employee.setForename(input);
                    break;
                case COLUMN_SURNAME:
                    employee.setSurname(input);
                    break;
                case COLUMN_ADDRESS:
                    employee.setAddress(input);
                    break;
                case COLUMN_PHONE:
                    employee.setPhoneNumber(input);
                    break;
                case COLUMN_EMAIL:
                    employee.setEmail(input);
                    break;

                default:
                    return;
            }

            employee.setEmployeeType(getEmployeeType());

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

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void setTextField() {
        final int COLUMN_USERNAME = 0;
        final int COLUMN_FORENAME = 1;
        final int COLUMN_SURNAME = 2;
        final int COLUMN_ADDRESS = 3;
        final int COLUMN_PHONE = 4;
        final int COLUMN_EMAIL = 5;

        switch (getChoice()) {
            case COLUMN_USERNAME:
                inputField.setText("Enter new username");
                inputField.setEnabled(true);
                break;
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
                inputField.setText("Please choose a value in the combobox below");
        }

    }


    private int getChoice() {
        return editEmployeeInfoCB.getSelectedIndex();
    }


    private void emptyTextField(String text) {
        if (inputField.getText().equals(text)) {
            inputField.setText("");
        }
    }

    private void createComboBox() {
        ArrayList<Employee> employeeList = EmployeeFactory.getAllEmployees();
        Integer[] columns = new Integer[]{EmployeeTableModel.COLUMN_USERNAME, EmployeeTableModel.COLUMN_FORENAME, EmployeeTableModel.COLUMN_SURNAME, EmployeeTableModel.COLUMN_ADDRESS, EmployeeTableModel.COLUMN_PHONE, EmployeeTableModel.COLUMN_EMAIL, EmployeeTableModel.COLUMN_EMPLOYEE_TYPE, EmployeeTableModel.COLUMN_ACTIVE};
        tableModel = new EmployeeTableModel(employeeList, columns);
        Object[] choices = new Object[tableModel.getColumnCount()];

        int tmp = 0;
        int unwantedColumn1 = 6, unwantedColumn2 = 7, unwantedColumn3 = 8;
        for (int i = 0; i < 8; i++) {
            if (i != unwantedColumn1 && i != unwantedColumn2 && i != unwantedColumn3) {
                choices[tmp] = tableModel.getColumnName(i);
                tmp++;
            }
        }
        editEmployeeInfoCB = new JComboBox(choices);
        editEmployeeInfoCB.setSelectedIndex(0);
    }

    private void createComboBox2() {
        /*ArrayList<Employee> employeeList = EmployeeFactory.getAllEmployees();
        Integer[] columns = new Integer[]{EmployeeTableModel.COLUMN_EMPLOYEE_TYPE};
        tableModel = new EmployeeTableModel(employeeList, columns);
        Object[] choices = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < columns.length; i++) {
            choices[i] = (new String(tableModel.getColumnName(i)));
        }*/

        Object[] choices = {"Employee", "Nutrition expert", "Salesperson", "Chauffeur", "Chef", "Administrator"};

        editEmployeeTypeCB = new JComboBox(choices);
        editEmployeeTypeCB.setSelectedIndex(0);
    }

    private int getChoice2() {
        return editEmployeeTypeCB.getSelectedIndex();
    }

    private EmployeeType getEmployeeType() {
        final int COLUMN_EMPLOYEE = 0;
        final int COLUMN_NUTRITION_EXPERT = 1;
        final int COLUMN_SALESPERSON = 2;
        final int COLUMN_CHAUFFEUR = 3;
        final int COLUMN_CHEF = 4;
        final int COLUMN_ADMINISTRATOR = 5;

        switch (getChoice2()) {
            case COLUMN_EMPLOYEE:
                return EmployeeType.EMPLOYEE;
            case COLUMN_NUTRITION_EXPERT:
                return EmployeeType.NUTRITION_EXPERT;
            case COLUMN_SALESPERSON:
                return EmployeeType.SALESPERSON;
            case COLUMN_CHAUFFEUR:
                return EmployeeType.CHAUFFEUR;
            case COLUMN_CHEF:
                return EmployeeType.CHEF;
            case COLUMN_ADMINISTRATOR:
                return EmployeeType.ADMINISTRATOR;

            default: return EmployeeType.EMPLOYEE;
        }
    }


    private void createCheckBox(){

    }

    private void createTextField(){
        inputField = new JTextField(20);
        inputField.setText("Choose a value in a combobox below");
        inputField.setEnabled(false);
        add(inputField);
    }

    private void createUIComponents(){
        createComboBox();
        createComboBox2();
        createCheckBox();
        createTextField();
    }

    public boolean getAddedNewValue() {
        return addedNewValue;
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

}
