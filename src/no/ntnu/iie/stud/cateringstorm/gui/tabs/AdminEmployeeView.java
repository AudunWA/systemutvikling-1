package no.ntnu.iie.stud.cateringstorm.gui.tabs;


import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddEmployeeDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditEmployeeDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EmployeeTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * GUI tab for administrators, giving an overview over employees
 */
public class AdminEmployeeView extends JPanel {
    private JPanel mainPanel;
    private JButton refreshButton;
    private JButton addEmployeeButton;
    private JButton editEmployeeButton;
    private JButton removeButton;
    private JCheckBox showInactiveEmployeesCheckBox;
    private JTextField searchField;
    private EmployeeTableModel tableModel;
    private JTable adminEmployeeTable;


    private ArrayList<Employee> employeeList;
    private Integer[] columns = new Integer[]{EmployeeTableModel.COLUMN_USERNAME, EmployeeTableModel.COLUMN_SURNAME, EmployeeTableModel.COLUMN_FORENAME, EmployeeTableModel.COLUMN_ADDRESS, EmployeeTableModel.COLUMN_PHONE, EmployeeTableModel.COLUMN_EMAIL, EmployeeTableModel.COLUMN_EMPLOYEE_TYPE, EmployeeTableModel.COLUMN_ACTIVE};

    public AdminEmployeeView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        addActionListeners();
    }

    private void addActionListeners() {
        refreshButton.addActionListener(e -> refresh());
        addEmployeeButton.addActionListener(e -> addEmployee());
        editEmployeeButton.addActionListener(e -> editEmployee(getSelectedEmployee()));
        removeButton.addActionListener(e -> removeEmployee(getSelectedEmployee()));
        showInactiveEmployeesCheckBox.addActionListener(e -> refresh());

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchField.setText("");
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDocument();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDocument();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchDocument();
            }

            public void searchDocument() {

                ArrayList<Employee> copy = new ArrayList<>();

                for (int i = 0; i < employeeList.size(); i++) {
                    if ((employeeList.get(i).getForename()).toLowerCase().contains(searchField.getText().toLowerCase()) || (employeeList.get(i).getSurname()).toLowerCase().contains(searchField.getText().toLowerCase())) {
                        copy.add(employeeList.get(i));
                    }
                }
                tableModel.setRows(copy);
            }
        });
    }

    /**
     *
     * @return Selected table row
     */
    private Employee getSelectedEmployee() {
        int selectedRow = adminEmployeeTable.getSelectedRow();
        if (selectedRow > -1) {
            Employee employee = tableModel.getValue(selectedRow);
            return employee;
        }
        return null;
    }

    private ArrayList<Employee> getActiveEmployees() {
        return EmployeeFactory.getActiveEmployees();
    }

    private ArrayList<Employee> getAllEmployees() {
        return EmployeeFactory.getAllEmployees();
    }

    private void createUIComponents() {
        createTable();
        createSearchField();
    }

    private void createTable() {
        employeeList = getActiveEmployees();

        columns = new Integer[]{EmployeeTableModel.COLUMN_USERNAME, EmployeeTableModel.COLUMN_SURNAME, EmployeeTableModel.COLUMN_FORENAME, EmployeeTableModel.COLUMN_ADDRESS, EmployeeTableModel.COLUMN_PHONE, EmployeeTableModel.COLUMN_EMAIL, EmployeeTableModel.COLUMN_EMPLOYEE_TYPE, EmployeeTableModel.COLUMN_ACTIVE};

        tableModel = new EmployeeTableModel(employeeList, columns);
        adminEmployeeTable = new JTable(tableModel);
        adminEmployeeTable.getTableHeader().setReorderingAllowed(false);
        adminEmployeeTable.setFillsViewportHeight(true);
    }

    /**
     * Opens the addEmployee GUI Dialog
     */
    private void addEmployee() {
        AddEmployeeDialog aeDialog = new AddEmployeeDialog();
        aeDialog.setVisible(true);
        if (aeDialog.hasAddedNewValue()) {
            refresh();
        }
        employeeList = EmployeeFactory.getActiveEmployees();
    }
    /**
     * Opens the editEmployee GUI Dialog
     */
    private void editEmployee(Employee employee) {
        if (employee != null) {
            EditEmployeeDialog eeDialog = new EditEmployeeDialog(employee);
            final int WIDTH = 300;
            final int HEIGHT = 300;
            eeDialog.pack();
            eeDialog.setSize(WIDTH, HEIGHT);
            eeDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            eeDialog.setVisible(true);
            if (eeDialog.getAddedNewValue()) {
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row in the employee table.");
            }
        }
    }
    /**
     * De-activates the selected row in the database
     */
    private void removeEmployee(Employee employee) {
        int selectedRow = adminEmployeeTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if (dialogResult == 0) {
            employee = tableModel.getValue(selectedRow);
            employee.setActive(false);
            EmployeeFactory.updateEmployee(employee);

            tableModel.removeRow(selectedRow);
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Employee removed").display();
        }
        refresh();
    }

    private void createSearchField() {
        searchField = new JTextField(20);
        setSearchField("Search for employee");
        add(searchField);
    }

    private void setSearchField(String text) {
        searchField.setText(text);
        searchField.setEnabled(true);
    }

    private void refresh() {
        if (showInactiveEmployeesCheckBox.isSelected()) {
            employeeList = EmployeeFactory.getAllEmployees();
        } else {
            employeeList = EmployeeFactory.getActiveEmployees();
        }
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Employees refreshed").display();
        tableModel.setRows(employeeList);
    }


    // Test method
    public static void main(String[] args) {
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new AdminEmployeeView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }
}