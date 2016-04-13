package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import java.awt.*;
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
    private JPanel buttonPanel;
    private JComboBox statusBox;
    private JTable orderTable;
    private JButton refreshButton;
    private JScrollPane scrollPane;
    OrderTableModel tableModel;

    private Employee employee;


    private static ArrayList<Order> orderList = new ArrayList<Order>();

    public SalespersonOrderView(Employee employee) {
        this.employee = employee;

        add(mainPanel);

        refreshButton.addActionListener(e -> {
            refresh();
        });

        addOrderButton.addActionListener(e -> {
            addOrder(employee);
        });

        editOrderButton.addActionListener(e -> {
            editOrder(getSelectedOrder());
        });

        statusBox.addActionListener(e -> {
            setStatus();
        });

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
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
        setScrollPane();
    }

    private void createTable() {
        orderList = OrderFactory.getAllOrders();
        Integer[] columns = new Integer[]{OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_DESCRIPTION, OrderTableModel.COLUMN_DELIVERY_TIME, OrderTableModel.COLUMN_ORDER_TIME, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_CUSTOMER_ID, OrderTableModel.COLUMN_STATUS_TEXT};
        tableModel = new OrderTableModel(orderList, columns);
        orderTable = new JTable(tableModel);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }

    private void setScrollPane(){
        scrollPane = new JScrollPane(orderTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1000,500));
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
        frame.add(new SalespersonOrderView(EmployeeFactory.getEmployee("chechter")));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }
}