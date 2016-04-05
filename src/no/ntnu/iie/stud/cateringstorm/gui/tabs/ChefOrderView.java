package no.ntnu.iie.stud.cateringstorm.gui.tabs;

//import no.ntnu.iie.stud.cateringstorm.gui.tabs.ChauffeurOrderView;

import no.ntnu.iie.stud.cateringstorm.gui.tableModels.ChefOrderTableModel;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Orderview for chefs. Chefs are able to edit contents of the order.
 * Created by Audun on 10.03.2016.
 */
public class ChefOrderView extends JPanel {
    private static final String WINDOW_TITLE = "Active orders";

    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JPanel buttonPanel;
    private JButton viewButton;
    private JButton editButton;
    private JComboBox statusBox;
    private JPanel cbPanel;
    private JButton refreshButton;
    private ChefOrderTableModel tableModel;
    private ArrayList<Order> orderList = new ArrayList<>();
    private String[] columnNames = {
            "OrderId","Description","Portions","Delivery time","Priority","Status"
    };
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
    }
    private void createTable(){
        orderList = OrderFactory.getAllOrders();

        tableModel = new ChefOrderTableModel(orderList,columnNames);
        orderTable = new JTable(tableModel);
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
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn = 5;
        boolean delivered = choice > 0;
        if(selectedRow > -1) {
            orderTable.clearSelection();
            tableModel.setValueAt((delivered) ? "Ready for delivery" : "In production", selectedRow, statusColumn);
        }
    }
    private void viewOrder(){
        // TODO: Implement method opening a new tab, allowing user to view more information of a single order
    }
    private void refresh(){
        //  TODO: Implement a method updating table for new orders, and removing changed orders from table.
    }
    public static void main(String[] args){
        final int WIDTH = 700;
        final int HEIGHT = 700;
        JFrame frame = new JFrame();
        frame.add(new ChefOrderView());
        frame.setVisible(true);
        frame.setTitle(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }
}
