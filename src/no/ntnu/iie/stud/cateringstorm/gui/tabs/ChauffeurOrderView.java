package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.Maps.MapBackend;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents GUI with overview of orders for the chauffeur
 * Created by Audun on 10.03.2016.
 */
public class ChauffeurOrderView extends JPanel {
    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JButton refreshButton;
    private JComboBox statusBox;
    private JPanel ComboBoxPanel;
    private JPanel ButtonPanel;
    private JButton startDeliveryButton;
    private JSpinner deliveryAmountSpinner;
    private ComboBoxModel cbModel;
    private static ArrayList<Order> orderList = new ArrayList<Order>();

    private void createTable(){
        orderList = OrderFactory.getAllOrdersChauffeur();
        Integer[] columns = new Integer[] {OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_CUSTOMER_NAME, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_ADDRESS, OrderTableModel.COLUMN_STATUS_TEXT, OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_DELIVERY_TIME};
        OrderTableModel tableModel = new OrderTableModel(orderList, columns);
        orderTable = new JTable(tableModel);
        getNewRenderedTable(orderTable);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }

    private static JTable getNewRenderedTable(final JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String temp = (String)table.getModel().getValueAt(row , 4);
                boolean priority = (boolean)table.getModel().getValueAt(row, 5);
                if (temp.equals("Ready for delivery") && !priority) {
                    setBackground(new Color(100,200,100));
                } else if (temp.equals("Delivered")) {
                    setBackground(new Color(150,150,150));
                } else if (temp.equals("Being delivered") && !priority){
                    setBackground(Color.ORANGE);
                } else if (priority) {
                    setBackground(new Color(200,100,100));
                    setFont(new Font("BOLD", Font.BOLD,12));
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
        return table;
    }

    public ChauffeurOrderView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        refreshButton.addActionListener(e-> {
            refresh();
        });

        statusBox.addActionListener(e -> {
            setStatus();
        });

        startDeliveryButton.addActionListener(e-> {
            makeDelivery();
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

    private void makeDelivery(){

        int amount = (Integer)deliveryAmountSpinner.getValue();
        if (amount < 1 || amount > 10){
            JOptionPane.showMessageDialog(this, "Please add a valid amount of Orders \n (from 1 - 10)");
            return;
        }

        ArrayList<String> addresses = OrderFactory.getAllAddresses();

        while (addresses.size() > amount){
            addresses.remove(addresses.size() - 1);
        }

        ArrayList<double[]> addressToPoint = new ArrayList<>();

        for (int i = 0; i < addresses.size(); i++){
            addressToPoint.add(MapBackend.addressToPoint(addresses.get(i)));
        }

        MapBackend.getShortestRoute(addressToPoint);


    }

    private void createComboBox(){
        Object[] status = {"Delivered","Ready for delivery"};

        statusBox = new JComboBox(status);
        statusBox.setSelectedIndex(0);
    }
    private void setStatus(){
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn= 5;
        boolean delivered = choice < 1;
        if(selectedRow > -1) {
            orderTable.clearSelection();

            orderTable.getModel().setValueAt((delivered) ? "Delivered" : "Not delivered", selectedRow, statusColumn);
        }
    }
    private void refresh(){
        // TODO: Implement method refresh() removing changed rows(delivered ones) and checking for new ones coming from the kitchen
        getReadyOrders();
    }
    private void getReadyOrders(){
        // TODO: Implement method loading orders with status "ready for delivery", changing status to "being delivered"
    }
    public static void main(String[] args){
        // Window dimensions
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new ChauffeurOrderView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }
}
