package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddCustomerDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditCustomerDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.CustomerTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by EliasBrattli on 06/04/2016.
 */

// TODO: Implement email, phone and address verification, and checking that names do not contain numbers and such!
public class SalespersonCustomerView extends JPanel{
    private JPanel mainPanel;
    private JTable customerTable;
    private JButton addButton;
    private JButton editButton;
    private JPanel selectButtonPanel;
    private JButton refreshButton;
    private JButton removeButton;
    private JPanel noSelectButtonPanel;
    private JLabel infoLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JCheckBox showInactiveCB;
    private JPanel noselectButtonPanel;
    private CustomerTableModel tableModel;

    public SalespersonCustomerView(){
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

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
        searchButton.addActionListener(e->{
            search();
        });
        showInactiveCB.addActionListener(e->{
            refresh();
        });
        searchField.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 setSearchField("");
                 searchButton.setEnabled(true);
             }
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
        createSearchField();
    }
    public void createTable() {
        ArrayList<Customer> customerList = getActiveCustomers();
        Integer[] columns = new Integer[]{CustomerTableModel.COLUMN_CUSTOMER_ID, CustomerTableModel.COLUMN_SURNAME,CustomerTableModel.COLUMN_FORENAME, CustomerTableModel.COLUMN_ADDRESS, CustomerTableModel.COLUMN_PHONE, CustomerTableModel.COLUMN_EMAIL, CustomerTableModel.COLUMN_ACTIVETEXT}; // Columns can be changed

        tableModel = new CustomerTableModel(customerList, columns);
        customerTable = new JTable(tableModel);
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerTable.setFillsViewportHeight(true);
    }

    private void addCustomer(){
        AddCustomerDialog acDialog = new AddCustomerDialog();
        acDialog.pack();
        final int WIDTH = 350, HEIGHT = 500;
        acDialog.setSize(WIDTH,HEIGHT);
        acDialog.setVisible(true);
        if(acDialog.hasAddedNewValue()){
            refresh();
        }
    }
    //Method opening a dialog for editing selected customer
    private void editCustomer(Customer customer){
        if(customer != null) {
            EditCustomerDialog ecDialog = new EditCustomerDialog(customer);
            final int WIDTH = 300;
            final int HEIGHT = 200;
            ecDialog.pack();
            ecDialog.setSize(WIDTH, HEIGHT);
            ecDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            ecDialog.setVisible(true);
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
    private void createSearchField(){
        searchField = new JTextField(20);
        setSearchField("Search customer name");
        add(searchField);
    }
    private void setSearchField(String text){
        searchField.setText(text);
        searchField.setEnabled(true);
    }


    // TODO: Eli has to fix buttons, they don't show
    private void search(){
        ArrayList<Customer> newRows;
        if(searchField.getText().trim().equals("")) {
            refresh();
        } else {
            newRows = CustomerFactory.getCustomersByQuery(searchField.getText());
            tableModel.setRows(newRows);
        }
    }


    private ArrayList<Customer> getActiveCustomers(){
        return CustomerFactory.getActiveCustomers();
    }

    private ArrayList<Customer> getAllCustomers(){
        return CustomerFactory.getAllCustomers();
    }

    private void refresh(){
        // TODO: Implement method refreshing data
        if(showInactiveCB.isSelected()) {
            tableModel.setRows(getAllCustomers());
        }else {
            tableModel.setRows(getActiveCustomers());
        }
    }

    public static void main(String[] args){
        // Window dimensions
        final int WIDTH = 1200;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new SalespersonCustomerView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }
}
