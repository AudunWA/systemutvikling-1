package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
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
    private JButton addCustomerButton;
    private JButton editCustomerButton;
    private JPanel buttonPanel;
    private JComboBox statusBox;
    private JTable orderTable;
    private JButton refreshButton;

    private static ArrayList<Order> orderList = new ArrayList<Order>();

    public SalespersonOrderView() {

        add(mainPanel);

        refreshButton.addActionListener(e -> {
            refresh();
        });

        statusBox.addActionListener(e -> {
            setStatus();
        });

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();
        createComboBox();
    }

    private void createTable() {
        orderList = OrderFactory.getAllOrders();
        Integer[] columns = new Integer[]{OrderTableModel.COLUMN_ORDER_TIME, OrderTableModel.COLUMN_STATUS_TEXT, OrderTableModel.COLUMN_DESCRIPTION};
        OrderTableModel tableModel = new OrderTableModel(orderList, columns);
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
        int statusColumn = 5;
        boolean delivered = choice < 1;
        if (selectedRow > -1) {
            orderTable.clearSelection();

            orderTable.getModel().setValueAt((delivered) ? "Active" : "Removed", selectedRow, statusColumn);
        }
    }

    private void refresh() {
        orderList = OrderFactory.getAllOrders();
        // TODO: Implement method refresh() removing changed rows(delivered ones) and checking for new ones coming from the kitchen
    }

    // FIXME: Add possibility to expand mainFrame for table
    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new SalespersonOrderView());
        frame.setVisible(true);
        frame.setTitle(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }
}