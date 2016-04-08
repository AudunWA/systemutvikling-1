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
    private static final String WINDOW_TITLE = "Customer Administration";

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
            removeCustomer();
        });
        refreshButton.addActionListener(e->{
            refresh();
        });
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }

    private Customer getSelectedCustomer(){
        return tableModel.getValue(customerTable.getSelectedRow());
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
        acDialog.setVisible(true);
    }
    private void editCustomer(Customer customer){
        // TODO: Implement class EditCustomerDialog
        EditCustomerDialog ecDialog = new EditCustomerDialog(customer);
    }
    private void removeCustomer(){
        // TODO: Implement a method setting customer status to "Not active"
    }
    private void refresh(){
        // TODO: Implement method refreshing data
        ((EntityTableModel)customerTable.getModel()).setRows(CustomerFactory.getAllCustomers());
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
        frame.setTitle(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }
}
