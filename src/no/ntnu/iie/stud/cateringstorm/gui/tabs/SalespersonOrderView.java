package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddSubscriptionDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;
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
 * Gives you options for adding, removing or editing orders
 */
public class SalespersonOrderView extends JPanel {
    private static ArrayList<Order> orderList = new ArrayList<Order>();
    OrderTableModel tableModel;
    private JPanel mainPanel;
    private JButton viewButton;
    private JButton addOrderButton;
    private JButton editOrderButton;
    private JComboBox statusBox;
    private JTable orderTable;
    private JButton refreshButton;
    private JButton searchButton;
    private JTextField searchField;

    public SalespersonOrderView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> refresh());
        addOrderButton.addActionListener(e -> addOrder(GlobalStorage.getLoggedInEmployee()));
        editOrderButton.addActionListener(e -> editOrder(getSelectedOrder()));
        statusBox.addActionListener(e -> setStatus());

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

                ArrayList<Order> copy = new ArrayList<>();

                for (int i = 0; i < orderList.size(); i++) {
                    if ((orderList.get(i).getCustomerName().toLowerCase().contains(searchField.getText().toLowerCase()) || (orderList.get(i).getCustomerAddress()).toLowerCase().contains(searchField.getText().toLowerCase()))) {
                        copy.add(orderList.get(i));

                    }
                }
                tableModel.setRows(copy);

            }
        });

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
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

    private Order getSelectedOrder() {
        return OrderFactory.getOrder((Integer)tableModel.getValueAt(orderTable.getSelectedRow(), 0));
    }

    private void addOrder(Employee employee) {
        // TODO: Open AddOrderDialog
        AddOrderDialog aoDialog = new AddOrderDialog(employee);
        final int WIDTH = 1000;
        final int HEIGHT = 400;
        aoDialog.pack();
        aoDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        aoDialog.setSize(WIDTH, HEIGHT);
        aoDialog.setLocationRelativeTo(aoDialog.getParent());

        aoDialog.setVisible(true);
        orderList = OrderFactory.getAllOrders();
    }

    private void editOrder(Order order) {
        System.out.println(order);
        if (order != null) {
            EditOrderDialog eoDialog = new EditOrderDialog(order);
            final int WIDTH = 300;
            final int HEIGHT = 200;
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
        Integer[] columns = new Integer[]{OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_DESCRIPTION, OrderTableModel.COLUMN_DELIVERY_TIME, OrderTableModel.COLUMN_ORDER_TIME, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_CUSTOMER_NAME, OrderTableModel.COLUMN_ADDRESS, OrderTableModel.COLUMN_STATUS_TEXT};
        tableModel = new OrderTableModel(orderList, columns);
        orderTable = new JTable(tableModel);
        orderTable.getTableHeader().setReorderingAllowed(false);
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

    private void createSearchField() {
        searchField = new JTextField(20);
        setSearchField("Search by customer name");
        add(searchField);
    }

    private void setSearchField(String text) {
        searchField.setText(text);
        searchField.setEnabled(true);
    }

    private void refresh() {
        tableModel.setRows(OrderFactory.getAllOrders());
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Orders refreshed.").display();
        orderList = OrderFactory.getAllOrders();
        // TODO: Implement method refresh() removing changed rows(delivered ones) and checking for new ones coming from the kitchen
    }
}