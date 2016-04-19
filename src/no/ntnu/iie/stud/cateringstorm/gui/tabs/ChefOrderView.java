package no.ntnu.iie.stud.cateringstorm.gui.tabs;

//import no.ntnu.iie.stud.cateringstorm.gui.tabs.ChauffeurOrderView;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.ChefMakeOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

/**
 * Orderview for chefs. Chefs are able to edit contents of the order.
 * Created by Audun on 10.03.2016.
 */
public class ChefOrderView extends JPanel {
    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JPanel buttonPanel;
    private JButton viewButton;
    private JComboBox statusBox;
    private JPanel cbPanel;
    private JButton refreshButton;
    private ArrayList<Order> orderList;
    private OrderTableModel tableModel;

    public ChefOrderView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

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
                Toast.makeText((JFrame)SwingUtilities.getWindowAncestor(this), "Orders status changed.", Toast.Style.SUCCESS).display();
            } else {
                Toast.makeText((JFrame)SwingUtilities.getWindowAncestor(this), "You cannot change this status.", Toast.Style.ERROR).display();
                //JOptionPane.showMessageDialog(this, "Error, chef can't change this status");
            }
        }
    }
    private void viewOrder(){
        // TODO: Implement method opening a new tab DishInfoView, allowing user to view more information of a single
        int viewedOrderId = (Integer) orderTable.getModel().getValueAt(orderTable.getSelectedRow(), 0);
        if (OrderFactory.getOrder(viewedOrderId).getStatus() != 0 && OrderFactory.getOrder(viewedOrderId).getStatus() != 3) {
            OrderFactory.setOrderState(viewedOrderId, 3);
            ChefMakeOrderDialog dialog = new ChefMakeOrderDialog(OrderFactory.getOrder(viewedOrderId));
            final int HEIGHT = 700;
            final int WIDTH = 1000;
            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setSize(WIDTH, HEIGHT);
            dialog.setLocationRelativeTo(dialog.getParent());

            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error this order is already made!");
        }
    }


    private void refresh(){
        //  TODO: Implement a method updating table for new orders, and removing changed orders from table.
        tableModel.setRows(getChefArray());
        Toast.makeText((JFrame)SwingUtilities.getWindowAncestor(this), "Orders refreshed.").display();
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
                    setBackground(new Color(100,200,100));
                } else if (temp.equals("Ready for delivery")) {
                    setBackground(new Color(150,150,150));
                } else if (temp.equals("In production") && !priority){
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
