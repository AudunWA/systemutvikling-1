package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddCustomerDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditCustomerDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.CustomerTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by EliasBrattli on 06/04/2016.
 */
public class SalespersonCustomerView extends JPanel{
    private JPanel mainPanel;
    private JTable customerTable;
    private JScrollPane tablePane;
    private JButton addButton;
    private JButton editButton;
    private JPanel buttonPanel;
    private JButton refreshButton;
    private JButton removeButton;
    private CustomerTableModel tableModel;

    public SalespersonCustomerView(){
        add(mainPanel);
        addButton.addActionListener(e->{
            addCustomer();
        });
        editButton.addActionListener(e->{
            editCustomer(getSelectedCustomer());
        });
        removeButton.addActionListener(e->{
            removeCustomer(getSelectedCustomer());
        });
        refreshButton.addActionListener(e->{
            refresh();
        });
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }

    private Customer getSelectedCustomer(){
        int selectedRow = customerTable.getSelectedRow();
        if(selectedRow > -1) {
            Customer customer = tableModel.getValue(selectedRow);
            return customer;
        }
        return null;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
        setScrollPane();
    }
    public void createTable() {
        ArrayList<Customer> customerList = CustomerFactory.getAllCustomers();
        Integer[] columns = new Integer[]{CustomerTableModel.COLUMN_CUSTOMER_ID, CustomerTableModel.COLUMN_SURNAME,CustomerTableModel.COLUMN_FORENAME, CustomerTableModel.COLUMN_ADDRESS, CustomerTableModel.COLUMN_PHONE, CustomerTableModel.COLUMN_EMAIL, CustomerTableModel.COLUMN_ACTIVETEXT}; // Columns can be changed

        tableModel = new CustomerTableModel(customerList, columns);
        customerTable = new JTable(tableModel);
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerTable.setFillsViewportHeight(true);
    }
    //Method used to set size for scroll pane containing JTable
    private void setScrollPane(){
        tablePane = new JScrollPane(customerTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tablePane.setPreferredSize(new Dimension(1000,700));
    }
    private void addCustomer(){
        // TODO: Implement AddCustomerDialog
        AddCustomerDialog acDialog = new AddCustomerDialog();
        acDialog.pack();
        final int WIDTH = 350, HEIGHT = 500;
        acDialog.setSize(WIDTH,HEIGHT);
        acDialog.setVisible(true);
        acDialog.setLocationRelativeTo(acDialog.getParent());
        if(acDialog.hasAddedNewValue()){
            refresh();
        }
    }
    private void editCustomer(Customer customer){
        // TODO: Implement class EditCustomerDialog
        if(customer != null) {
            EditCustomerDialog ecDialog = new EditCustomerDialog(customer);
            final int WIDTH = 300;
            final int HEIGHT = 200;
            ecDialog.pack();
            ecDialog.setSize(WIDTH, HEIGHT);
            ecDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            ecDialog.setVisible(true);
            ecDialog.setLocationRelativeTo(ecDialog.getParent());
            if(ecDialog.getAddedNewValue()){
                refresh();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please select a row in the customer table");
        }
    }
    private void removeCustomer(Customer customer){
        // TODO: Implement a method setting customer status to "Not active"
        int activeColumn = tableModel.COLUMN_ACTIVETEXT;
        int selectedRow = customerTable.getSelectedRow();
        if(customer != null ) {
            // TODO: Fill code here
            customerTable.clearSelection();
            customerTable.getModel().setValueAt(false,selectedRow,activeColumn);
            refresh();
        }else{
            JOptionPane.showMessageDialog(null, "Please select a row in the customer table");
        }
    }
    // TODO: Eli has to fix buttons, they dont show
    /*
    searchButton.addActionListener(e -> {
        ArrayList<Customer> newRows;
        if(searchTextField.getText().trim().equals("")) {
            newRows = CustomerFactory.getAllCustomers();
        } else {
            newRows = CustomerFactory.getCustomersByQuery(searchTextField.getText());
        }
        tableModel.setRows(newRows);
    });
    */
    private void refresh(){
        // TODO: Implement method refreshing data
        tableModel.setRows(CustomerFactory.getAllCustomers());
    }
    public CustomerTableModel getTableModel(){
        return tableModel;
    }
    public static void main(String[] args){
        // Window dimensions
        final int WIDTH = 1200;
        final int HEIGHT = 800;
        JFrame frame = new JFrame();
        frame.add(new SalespersonCustomerView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }
}
