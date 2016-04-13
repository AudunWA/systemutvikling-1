package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private JTextField infoText;
    private ComboBoxModel cbModel;
    private static ArrayList<Order> orderList = new ArrayList<Order>();

    private void createTable(){
        orderList = OrderFactory.getAllOrders();
        Integer[] columns = new Integer[] {OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_CUSTOMER_ID, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_DELIVERY_TIME, OrderTableModel.COLUMN_ADDRESS, OrderTableModel.COLUMN_STATUS_TEXT};
        OrderTableModel tableModel = new OrderTableModel(orderList, columns);
        orderTable = new JTable(tableModel);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
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

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();
        createComboBox();
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
    }

    // FIXME: Add possibility to expand mainFrame for table
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
