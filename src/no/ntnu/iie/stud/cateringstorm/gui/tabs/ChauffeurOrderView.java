package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Coordinate;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.maps.MapBackend;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents GUI with overview of orders for the chauffeur
 */
public class ChauffeurOrderView extends JPanel {
    private static ArrayList<Order> orderList = new ArrayList<>(),
            helpTable = new ArrayList<>();
    private JPanel mainPanel;
    private JTable orderTable;
    private JButton refreshButton;
    private JComboBox<String> statusBox;
    private JButton startDeliveryButton;
    private JSpinner deliveryAmountSpinner;
    private OrderTableModel tableModel;

    public ChauffeurOrderView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        addActionListeners();
    }

    private static JTable getNewRenderedTable(final JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String temp = (String) table.getModel().getValueAt(row, 4);
                Timestamp deliveryDate = (Timestamp) table.getModel().getValueAt(row, 6);
                boolean priority = (boolean) table.getModel().getValueAt(row, 5);
                if (deliveryDate.toLocalDateTime().toLocalDate().isAfter(LocalDate.now())) {
                    setBackground(new Color(150, 150, 150));
                } else if (temp.equals("Ready for delivery") && !priority) {
                    setBackground(new Color(100, 200, 100));
                } else if (temp.equals("Delivered")) {
                    setBackground(new Color(150, 150, 150));
                } else if (temp.equals("Being delivered")) {
                    setBackground(Color.ORANGE);
                } else if (priority) {
                    setBackground(new Color(200, 100, 100));
                    setFont(new Font("BOLD", Font.BOLD, 12));
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
        return table;
    }

    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new ChauffeurOrderView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }

    private void addActionListeners() {
        refreshButton.addActionListener(e -> refresh());
        statusBox.addActionListener(e -> setStatus());
        startDeliveryButton.addActionListener(e -> makeDelivery());
    }

    private void createTable() {
        orderList = OrderFactory.getAllAvailableOrdersForChauffeurTable();
        for (int i = 0; i < orderList.size(); i++) {
            if (OrderFactory.getDeliveryStart(orderList.get(i).getOrderId()) != null) {
                if (OrderFactory.getDeliveryStart(orderList.get(i).getOrderId()).before(new Date(System.currentTimeMillis() - 3600000 * 3))) {
                    OrderFactory.setOrderState(orderList.get(i).getOrderId(), 0);
                }
            }
        }
        Integer[] columns = new Integer[]{OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_CUSTOMER_NAME, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_ADDRESS, OrderTableModel.COLUMN_STATUS_TEXT, OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_DELIVERY_TIME};
        tableModel = new OrderTableModel(orderList, columns);
        orderTable = new JTable(tableModel);
        getNewRenderedTable(orderTable);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderTable.setFillsViewportHeight(true);
    }

    private void createUIComponents() {
        createTable();
        createComboBox();
    }

    private void makeDelivery() {

        startDeliveryButton.setEnabled(false);

        int amount = (Integer) deliveryAmountSpinner.getValue();
        if (amount < 1 || amount > 10) {
            JOptionPane.showMessageDialog(this, "Please add a valid amount of Orders \n (from 1 - 10)");
            startDeliveryButton.setEnabled(true);
            return;
        }

        ArrayList<String> addresses = OrderFactory.getAllAvailableDeliveryAddresses();
        helpTable = OrderFactory.getAllAvailableOrdersChauffeur();

        if (helpTable.size() < amount) {
            JOptionPane.showMessageDialog(this, "This amount of orders is not available at this moment");
            startDeliveryButton.setEnabled(true);
            return;
        }

        while (addresses.size() > amount) {
            addresses.remove(addresses.size() - 1);
            helpTable.remove(helpTable.size() - 1);
        }

        for (Order order : helpTable) {
            OrderFactory.setOrderState(order.getOrderId(), 4);
            OrderFactory.setDeliveryStart(order.getOrderId());
        }

        ArrayList<Coordinate> addressToPoint = new ArrayList<>();

        for (String address : addresses) {
            Coordinate coordinate = MapBackend.addressToPoint(address + ", Trondheim, Norway");
            if (coordinate == null) {
                JOptionPane.showMessageDialog(this, "Address \"" + address + "\" is troublesome.");
                startDeliveryButton.setEnabled(true);
                return;
            }
            addressToPoint.add(coordinate);
        }

        addressToPoint = MapBackend.getShortestRoute(addressToPoint);

        // Display map
        MapView mapView = new MapView(addressToPoint, helpTable);
        mapView.setSize(700, 500);
        mapView.setVisible(true);

        startDeliveryButton.setEnabled(true);

        // Reset states
        if (!mapView.isSuccess()) {
            for (Order order : helpTable) {
                OrderFactory.setOrderState(order.getOrderId(), 0);
            }
        }
        refresh();
    }

    private void createComboBox() {
        String[] status = {"Delivered", "Ready for delivery"};

        statusBox = new JComboBox<>(status);
        statusBox.setSelectedIndex(0);
    }

    private void setStatus() {
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn = 5;
        boolean delivered = choice < 1;
        if (selectedRow > -1) {
            orderTable.clearSelection();

            orderTable.getModel().setValueAt((delivered) ? "Delivered" : "Being delivered", selectedRow, statusColumn);
        }
    }

    private void refresh() {
        orderList = OrderFactory.getAllAvailableOrdersForChauffeurTable();
        tableModel.setRows(orderList);
        for (int i = 0; i < orderList.size(); i++) {
            if (OrderFactory.getDeliveryStart(i) != null) {
                if (OrderFactory.getDeliveryStart(i).after(new Date(System.currentTimeMillis() + 3600000 * 3))) {
                    OrderFactory.setOrderState(i, 0);
                }
            }
        }
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Orders refreshed.").display();
    }
}
