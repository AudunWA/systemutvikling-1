package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.order.ChauffeurOrderTableModel;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Represents GUI with overview of orders for the chauffeur
 * Created by Audun on 10.03.2016.
 */
public class ChauffeurOrderView extends JPanel {
    private static final String WINDOW_TITLE = "Active orders";


    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JButton homeButton;
    private JComboBox statusBox;
    private JPanel ComboBoxPanel;
    private JPanel ButtonPanel;
    private JTextField infoText;
    private ComboBoxModel cbModel;
    private ChauffeurOrderTableModel tableModel;
    private static ArrayList<Order> orderList = new ArrayList<Order>();


    public ChauffeurOrderView() {

        add(mainPanel);
        homeButton.addActionListener(e-> {
            //Change window
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

    private void createTable(){
        orderList = OrderFactory.getAllOrders();

        tableModel = new ChauffeurOrderTableModel(orderList);
        orderTable = new JTable(tableModel);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }


    private void createComboBox(){
        Object[] status = {"Delivered","Ready for delivery"};

        statusBox = new JComboBox(status);
        statusBox.setSelectedIndex(0);
    }
    // FIXME: Check trouble with wrongly selected indexes in combobox
    private void setStatus(){
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn= 5;
        boolean delivered = choice > 0;
        int arrLength = tableModel.getRowCount()-1;
        if(selectedRow > -1) {
            orderTable.clearSelection();

            tableModel.setValueAt((delivered) ? "Delivered" : "Not delivered", selectedRow, statusColumn);
        }
    }

    // FIXME: Add possibility to expand mainFrame for table
    public static void main(String[] args){
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new ChauffeurOrderView());
        frame.setVisible(true);
        frame.setTitle(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }
}
