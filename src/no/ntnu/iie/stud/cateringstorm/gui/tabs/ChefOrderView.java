package no.ntnu.iie.stud.cateringstorm.gui.tabs;

//import no.ntnu.iie.stud.cateringstorm.gui.tabs.ChauffeurOrderView;

import no.ntnu.iie.stud.cateringstorm.entities.order.ChauffeurOrderTableModel;
import no.ntnu.iie.stud.cateringstorm.entities.order.ChefOrderTableModel;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Orderview for chefs. Chefs are able to edit contents of the order.
 * Created by Audun on 10.03.2016.
 */
public class ChefOrderView extends JFrame {
    private static final String WINDOW_TITLE = "Active orders";

    // Window dimensions
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JPanel buttonPanel;
    private JButton viewButton;
    private JButton editButton;
    private JButton saveButton;
    private JComboBox statusBox;
    private JPanel cbPanel;
    private ComboBoxModel cbModel;
    private ChefOrderTableModel tableModel;
    private ArrayList<Order> orderList;
    public ChefOrderView() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        viewButton.addActionListener(e -> {
            //TODO: Implement method opening a new tab, allowing user to view more information of a single order
        });
        statusBox.addActionListener(e -> {
            //TODO: Implement method editing status of an order
            setStatus();
        });
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();
        createComboBox();
    }
    private void createTable(){
        orderList = OrderFactory.getAllOrders();

        tableModel = new ChefOrderTableModel(orderList);
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
    private void setStatus(){
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int deliveryRow = 5;
        boolean delivered = choice > 0;
        int arrLength = tableModel.getRowCount()-1;
        orderTable.clearSelection();
        //To update database
        //OrderFactory.setOrderState(tableModel.getOrder(selectedRow).getOrderId(),delivered);
        tableModel.setValueAt((delivered)?"Ready for delivery":"In production",selectedRow,deliveryRow);
    }
    public static void main(String[] args){
        ChefOrderView overView = new ChefOrderView();
        overView.setVisible(true);
    }
}
