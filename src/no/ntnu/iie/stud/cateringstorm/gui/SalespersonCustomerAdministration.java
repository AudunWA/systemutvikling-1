package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.gui.tableModels.ChefOrderTableModel;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;

import javax.swing.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Created by kenan on 30.03.2016.
 */
public class SalespersonCustomerAdministration extends JPanel{

    private static final String WINDOW_TITLE = "Menu Administrator";

    // Window dimensions
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private JPanel mainPanel;
    private JButton viewButton;
    private JButton addCustomerButton;
    private JButton editCustomerButton;
    private JTable table1;
    private JComboBox statusBox;
    private JButton refreshButton;

    public SalespersonCustomerAdministration() {

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



    }

    private void createUIComponents() {
        createTable();
        createComboBox();
    }
    private void createTable(){
        orderList = OrderFactory.getAllOrders();
        
        tableModel = new no.ntnu.iie.stud.cateringstorm.gui.tablemodels.ChefOrderTableModel(orderList,columnNames);
        orderTable = new JTable(tableModel);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }

    private void viewOrder(){
        // TODO: Implement method opening a new tab, allowing user to view more information of a single order
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void refresh(){
        //  TODO: Implement a method updating table for new orders, and removing changed orders from table.
    }

    private void setStatus(){
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn = 5;
        boolean inProduction = choice > 0;
        if(selectedRow > -1) {
            orderTable.clearSelection();
            // tableModel.setValueAt((inProduction) ? "Ready for delivery" : "In production", selectedRow, statusColumn);
        }
    }
}
