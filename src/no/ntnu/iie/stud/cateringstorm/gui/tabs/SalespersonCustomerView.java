package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddCustomerDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditCustomerDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.CustomerTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Gives you options for adding, editing and removing customers.
 */

public class SalespersonCustomerView extends JPanel {
    private static final Integer[] COLUMNS = new Integer[]{CustomerTableModel.COLUMN_SURNAME, CustomerTableModel.COLUMN_FORENAME, CustomerTableModel.COLUMN_ADDRESS, CustomerTableModel.COLUMN_PHONE, CustomerTableModel.COLUMN_EMAIL, CustomerTableModel.COLUMN_ACTIVE}; // Columns can be changed
    private static final Integer[] ADMIN_COLUMNS = new Integer[]{CustomerTableModel.COLUMN_CUSTOMER_ID, CustomerTableModel.COLUMN_SURNAME, CustomerTableModel.COLUMN_FORENAME, CustomerTableModel.COLUMN_ADDRESS, CustomerTableModel.COLUMN_PHONE, CustomerTableModel.COLUMN_EMAIL, CustomerTableModel.COLUMN_ACTIVE}; // Columns can be changed

    private JPanel mainPanel;
    private JTable customerTable;
    private JButton addButton;
    private JButton editButton;
    private JButton refreshButton;
    private JButton removeButton;
    private JTextField searchField;
    private JButton searchButton;
    private JCheckBox showInactiveCB;
    private JPanel noselectButtonPanel;
    private CustomerTableModel tableModel;

    private ArrayList<Customer> customerList;

    public SalespersonCustomerView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);


    }
    public void addActionListeners(){
        addButton.addActionListener(e -> addCustomer());
        editButton.addActionListener(e -> editCustomer(getSelectedCustomer()));
        removeButton.addActionListener(e -> removeCustomer(getSelectedCustomer()));
        refreshButton.addActionListener(e -> refresh());
        showInactiveCB.addActionListener(e -> refresh());

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

                ArrayList<Customer> copy = new ArrayList<>();

                for (int i = 0; i < customerList.size(); i++) {
                    if ((customerList.get(i).getForename()).toLowerCase().contains(searchField.getText().toLowerCase()) || (customerList.get(i).getSurname()).toLowerCase().contains(searchField.getText().toLowerCase())) {
                        copy.add(customerList.get(i));

                    }
                }
                tableModel.setRows(copy);

            }
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSearchField("");
            }
        });
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }
    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 1200;
        final int HEIGHT = 600;
        GlobalStorage.setLoggedInEmployee(EmployeeFactory.getEmployee("chechter"));
        JFrame frame = new JFrame();
        frame.add(new SalespersonCustomerView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }

    private Customer getSelectedCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow > -1) {
            Customer customer = tableModel.getValue(selectedRow);
            return customer;
        }
        return null;
    }

    private void createUIComponents() {
        createTable();
        createSearchField();
    }

    private void createTable() {

        customerList = getActiveCustomers();

        if (GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            tableModel = new CustomerTableModel(customerList, ADMIN_COLUMNS);
        } else {
            tableModel = new CustomerTableModel(customerList, COLUMNS);
        }

        customerTable = new JTable(tableModel);
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerTable.setFillsViewportHeight(true);
    }

    private void addCustomer() {
        AddCustomerDialog acDialog = new AddCustomerDialog();
        acDialog.pack();
        final int WIDTH = 350, HEIGHT = 500;
        acDialog.setSize(WIDTH, HEIGHT);
        acDialog.setVisible(true);
        if (acDialog.hasAddedNewValue()) {
            refresh();
        }else{
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Customer was not added.", Toast.Style.ERROR).display();
        }
        //customerList = CustomerFactory.getActiveCustomers();
    }

    //Method opening a dialog for editing selected customer
    private void editCustomer(Customer customer) {
        if (customer != null) {
            EditCustomerDialog ecDialog = new EditCustomerDialog(customer);
            final int WIDTH = 300;
            final int HEIGHT = 400;
            ecDialog.pack();
            ecDialog.setSize(WIDTH, HEIGHT);
            ecDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            ecDialog.setVisible(true);
            if (ecDialog.getAddedNewValue()) {
                refresh();
            }else{
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Customer was not edited.", Toast.Style.ERROR).display();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row in the customer table");
        }
    }

    private void removeCustomer(Customer customer) {
        // TODO: Implement a method setting customer status to "Not active"
        int activeColumn = CustomerTableModel.COLUMN_ACTIVETEXT;
        int selectedRow = customerTable.getSelectedRow();
        if (customer != null) {
            // TODO: Fill code here
            customerTable.clearSelection();
            customerTable.getModel().setValueAt(false, selectedRow, activeColumn);
            refresh();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row in the customer table");
        }
    }

    private void createSearchField() {
        searchField = new JTextField(20);
        setSearchField("Search customer name");
        add(searchField);
    }

    private void setSearchField(String text) {
        searchField.setText(text);
        searchField.setEnabled(true);
    }

    private ArrayList<Customer> getActiveCustomers() {
        return CustomerFactory.getActiveCustomers();
    }

    private ArrayList<Customer> getAllCustomers() {
        return CustomerFactory.getAllCustomers();
    }

    private void refresh() {
        if (showInactiveCB.isSelected()) {
            customerList = CustomerFactory.getAllCustomers();
        } else {
            customerList = CustomerFactory.getActiveCustomers();
        }
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Customers refreshed.").display();
        tableModel.setRows(customerList);
    }
}
