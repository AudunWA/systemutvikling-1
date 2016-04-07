package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kenan on 30.03.2016.
 */
public class SalespersonOrderView extends JPanel {

    private static final String WINDOW_TITLE = "Menu Administrator";

    // Window dimensions
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

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

    private static ArrayList<Order> orderList = new ArrayList<Order>();

    public SalespersonOrderView() {

        add(mainPanel);

        refreshButton.addActionListener(e -> {
            refresh();
        });

        addOrderButton.addActionListener(e -> {
            addOrder();
        });

        editOrderButton.addActionListener(e -> {
            editOrder();
        });

        statusBox.addActionListener(e -> {
            setStatus();
        });

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });


    }

    private void addOrder(){

    }

    private void editOrder(){

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
        OrderTableModel tableModel = new OrderTableModel(orderList, columns);
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
            orderTable.clearSelection();

            orderTable.getModel().setValueAt((active) ? "Activate" : "Removed", selectedRow, statusColumn);
        }
    }

    private void refresh() {
        ((EntityTableModel)orderTable.getModel()).setRows(OrderFactory.getAllOrders());
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
        frame.setTitle(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }
}