package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by kenan on 30.03.2016.
 */
public class SalespersonOrderView extends JPanel {
    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JButton viewButton;
    private JButton addOrderButton;
    private JButton editOrderButton;
    private JPanel selectButtonPanel;
    private JComboBox statusBox;
    private JTable orderTable;
    private JButton refreshButton;
    private JPanel noSelectButtonPanel;
    private JLabel infoLabel;
    private JButton searchButton;
    private JTextField searchField;
    OrderTableModel tableModel;

    private static ArrayList<Order> orderList = new ArrayList<Order>();

    public SalespersonOrderView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> {
            refresh();
        });

        addOrderButton.addActionListener(e -> {
            addOrder(GlobalStorage.getLoggedInEmployee());
        });

        editOrderButton.addActionListener(e -> {
            editOrder(getSelectedOrder());
        });

        statusBox.addActionListener(e -> {
            setStatus();
        });
        searchButton.addActionListener(e->{
            search();
        });
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSearchField("");
                searchButton.setEnabled(true);
            }
        });
    }
    private Order getSelectedOrder(){
        return tableModel.getValue(orderTable.getSelectedRow());
    }

    private void addOrder(Employee employee){
        // TODO: Open AddOrderDialog
        AddOrderDialog aoDialog = new AddOrderDialog(employee);
        final int WIDTH = 500;
        final int HEIGHT = 400;
        aoDialog.pack();
        aoDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        aoDialog.setSize(WIDTH, HEIGHT);
        aoDialog.setLocationRelativeTo(aoDialog.getParent());

        aoDialog.setVisible(true);
    }

    private void editOrder(Order order){
        if (order != null) {
            EditOrderDialog eoDialog = new EditOrderDialog(order);
            final int WIDTH = 500;
            final int HEIGHT = 400;
            eoDialog.pack();
            eoDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            eoDialog.setSize(WIDTH, HEIGHT);
            eoDialog.setLocationRelativeTo(eoDialog.getParent());

            eoDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row in order table");
        }
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();
        createComboBox();
        createSearchField();
    }

    private void createTable() {
        orderList = OrderFactory.getAllOrders();
        Integer[] columns = new Integer[]{OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_DESCRIPTION, OrderTableModel.COLUMN_DELIVERY_TIME, OrderTableModel.COLUMN_ORDER_TIME, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_CUSTOMER_NAME,OrderTableModel.COLUMN_ADDRESS, OrderTableModel.COLUMN_STATUS_TEXT};
        tableModel = new OrderTableModel(orderList, columns);
        orderTable = new JTable(tableModel);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }

    private void createComboBox() {
        Object[] status = {"Activate", "Remove"};

        statusBox = new JComboBox(status);
        statusBox.setSelectedIndex(0);
    }

    // FIXME: Check trouble with wrongly selected indexes in combobox
    private void setStatus() {
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn = 7;
        boolean active = choice < 1;
        if (selectedRow > -1) {
            if (orderList.get(selectedRow).getStatus() != 2 && orderList.get(selectedRow).getStatus() != 0) {
                orderTable.clearSelection();
                orderTable.getModel().setValueAt((active) ? "Activate" : "Removed", selectedRow, statusColumn);
            } else {
                JOptionPane.showMessageDialog(this, "Salesperson can't change this status");
            }
        }
    }
    private void createSearchField(){
        searchField = new JTextField(20);
        setSearchField("Search by customer name");
        add(searchField);
    }
    private void setSearchField(String text){
        searchField.setText(text);
        searchField.setEnabled(true);
    }


    private void search(){
        ArrayList<Order> newRows;
        if(searchField.getText().trim().equals("")) {
            refresh();
        } else {
            newRows = OrderFactory.getOrdersByQuery(searchField.getText());
            tableModel.setRows(newRows);
        }
    }
    private void refresh() {
        tableModel.setRows(OrderFactory.getAllOrders());
        // TODO: Implement method refresh() removing changed rows(delivered ones) and checking for new ones coming from the kitchen
    }

    // FIXME: Add possibility to expand mainFrame for table
    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new SalespersonOrderView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }
}