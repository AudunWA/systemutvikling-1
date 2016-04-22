package no.ntnu.iie.stud.cateringstorm.gui.tabs;


import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddEmployeeDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EmployeeTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditEmployeeDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


import java.util.ArrayList;

/**
 * Created by Håvard
 */
public class AdminEmployeeView extends JPanel{
    private JPanel mainPanel;
    private JButton refreshButton;
    private JButton addEmployeeButton;
    private JButton editEmployeeButton;
    private JButton removeButton;
    private JPanel noSelectButtonPanel;
    private JCheckBox showInactiveEmployeeButton;
    private JTextField searchField;
    private EmployeeTableModel tableModel;
    private JTable adminEmployeeTable;


    private ArrayList<Employee> employeeList;


    public AdminEmployeeView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> {
            refresh();
        });

        addEmployeeButton.addActionListener(e -> {
            addEmployee();
        });

        editEmployeeButton.addActionListener(e -> {
            editEmployee(getSelectedEmployee());
        });

        removeButton.addActionListener(e -> {
            removeEmployee(getSelectedEmployee());
        });

        showInactiveEmployeeButton.addActionListener(e -> {
            refresh();
        });

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

    private Employee getSelectedEmployee(){
        int selectedRow = adminEmployeeTable.getSelectedRow();
        if(selectedRow > -1){
            Employee employee = tableModel.getValue(selectedRow);
            return employee;
        }
        return null;
    }

    private Integer[] columns = new Integer[]{EmployeeTableModel.COLUMN_USERNAME, EmployeeTableModel.COLUMN_SURNAME, EmployeeTableModel.COLUMN_FORENAME, EmployeeTableModel.COLUMN_ADDRESS, EmployeeTableModel.COLUMN_PHONE, EmployeeTableModel.COLUMN_EMAIL, EmployeeTableModel.COLUMN_EMPLOYEE_TYPE, EmployeeTableModel.COLUMN_ACTIVE};

    private ArrayList<Employee> getActiveEmployees(){
        return EmployeeFactory.getActiveEmployees();
    }

    private ArrayList<Employee> getAllEmployees(){
        return EmployeeFactory.getAllEmployees();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
        createSearchField();
    }

    private void createTable(){
        employeeList = getActiveEmployees();

        columns = new Integer[]{EmployeeTableModel.COLUMN_USERNAME, EmployeeTableModel.COLUMN_SURNAME, EmployeeTableModel.COLUMN_FORENAME, EmployeeTableModel.COLUMN_ADDRESS, EmployeeTableModel.COLUMN_PHONE, EmployeeTableModel.COLUMN_EMAIL, EmployeeTableModel.COLUMN_EMPLOYEE_TYPE, EmployeeTableModel.COLUMN_ACTIVE};

        tableModel = new EmployeeTableModel(employeeList, columns);
        adminEmployeeTable = new JTable(tableModel);
        adminEmployeeTable.getTableHeader().setReorderingAllowed(false);
        adminEmployeeTable.setFillsViewportHeight(true);
    }

    private void addEmployee(){
        AddEmployeeDialog aeDialog = new AddEmployeeDialog();
        aeDialog.pack();
        final int WIDTH = 400;
        final int HEIGHT = 400;
        aeDialog.setSize(WIDTH, HEIGHT);
        aeDialog.setVisible(true);
        if(aeDialog.hasAddedNewValue()){
            refresh();
        }
        employeeList = EmployeeFactory.getActiveEmployees();
    }

    private void editEmployee(Employee employee){
        if(employee != null){
            EditEmployeeDialog eeDialog = new EditEmployeeDialog(employee);
            final int WIDTH = 300;
            final int HEIGHT = 300;
            eeDialog.pack();
            eeDialog.setSize(WIDTH, HEIGHT);
            eeDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            eeDialog.setVisible(true);
            if(eeDialog.getAddedNewValue()){
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row in the employee table.");
            }
        }
    }


    private void removeEmployee(Employee employee){
        int activeColumn = tableModel.COLUMN_ACTIVE;
        int selectedRow = adminEmployeeTable.getSelectedRow();
        if(employee != null){
            adminEmployeeTable.clearSelection();
            adminEmployeeTable.getModel().setValueAt(false, selectedRow, activeColumn);
            refresh();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row in the employee table.");
        }
    }


    private void createSearchField(){
        searchField = new JTextField(20);
        setSearchField("Search for employee");
        add(searchField);
    }

    private void setSearchField(String text){
        searchField.setText(text);
        searchField.setEnabled(true);
    }


    private void refresh(){
        if(showInactiveEmployeeButton.isSelected()){
            tableModel.setRows(getAllEmployees());
        } else {
            tableModel.setRows(getActiveEmployees());
        }
        Toast.makeText((JFrame)SwingUtilities.getWindowAncestor(this),"Employees refreshed").display();
        employeeList = EmployeeFactory.getActiveEmployees();
    }

    public static void main(String[] args){
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

/**/