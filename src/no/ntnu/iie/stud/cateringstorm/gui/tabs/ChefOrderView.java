package no.ntnu.iie.stud.cateringstorm.gui.tabs;

//import no.ntnu.iie.stud.cateringstorm.gui.tabs.ChauffeurOrderView;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.ChefMakeOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * Orderview for chefs. Chefs are able to edit contents of the order.
 * Created by Audun on 10.03.2016.
 */
public class ChefOrderView extends JPanel {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JPanel buttonPanel;
    private JButton viewButton;
    private JComboBox statusBox;
    private JPanel cbPanel;
    private JButton refreshButton;
    private ArrayList<Order> orderList = new ArrayList<>();
    private   OrderTableModel tableModel;

    public ChefOrderView() {
        add(mainPanel);
        viewButton.addActionListener(e -> {
            viewOrder();
        });
        statusBox.addActionListener(e -> {
            setStatus();
        });
        refreshButton.addActionListener(e->{
            refresh();
        });
    }
    //Custom initialization of UI components
    private void createUIComponents() {
        createTable();
        createComboBox();
        setScrollPane();
    }

    private ArrayList<Order> getChefArray() {
        orderList = OrderFactory.getAllOrdersChef();
        return orderList;
    }

    private void createTable(){
        getChefArray();

        Integer[] columns = new Integer[] { OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_DESCRIPTION, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_STATUS_TEXT, OrderTableModel.COLUMN_DELIVERY_TIME};
        tableModel = new OrderTableModel(orderList,columns);
        orderTable = new JTable(tableModel);
        getNewRenderedTable(orderTable);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }

    private void setScrollPane(){
        scrollPane = new JScrollPane(orderTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1000,500));
    }


    private void createComboBox(){
            Object[] status = {"In production","Ready for delivery"};
            statusBox = new JComboBox(status);
            statusBox.setSelectedIndex(0);
    }

    // FIXME: Trouble with wrongly selected indexes. Might be wrong logic i back-end?
    private void setStatus(){
        orderList = OrderFactory.getAllOrders();
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn = 5;
        boolean inProduction = choice > 0;
        if(selectedRow > -1) {
            if (orderList.get(selectedRow).getStatus() < 2) {
                orderTable.clearSelection();
                orderTable.getModel().setValueAt((inProduction) ? "Ready for delivery" : "In production", selectedRow, statusColumn);
            } else {
                JOptionPane.showMessageDialog(this, "Error, chef can't change this status");
            }
        }
    }
    private void viewOrder(){
        // TODO: Implement method opening a new tab DishInfoView, allowing user to view more information of a single order
        int viewedOrderId = (Integer)orderTable.getModel().getValueAt(orderTable.getSelectedRow(), 0);
        ChefMakeOrderDialog dialog = new ChefMakeOrderDialog(OrderFactory.getOrder(viewedOrderId));

    }


    private void refresh(){
        //  TODO: Implement a method updating table for new orders, and removing changed orders from table.
        tableModel.setRows(getChefArray());
    }

    private static JTable getNewRenderedTable(final JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String temp = (String)table.getModel().getValueAt(row , 4);
                boolean priority = (boolean)table.getModel().getValueAt(row, 3);
                if (temp.equals("Ready for production") && !priority) {
                    setBackground(Color.ORANGE);
                } else if (temp.equals("Ready for delivery") && !priority) {
                    setBackground(Color.GREEN);
                } else if (priority) {
                    setBackground(Color.red);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
        return table;
    }

    public static void main(String[] args){
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new ChefOrderView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }
}
