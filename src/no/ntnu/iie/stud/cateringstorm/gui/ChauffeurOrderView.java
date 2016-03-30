package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;

import javax.swing.*;
import java.time.LocalDate;

/**
 * Represents GUI with overview of orders for the chauffeur
 * Created by Audun on 10.03.2016.
 */
public class ChauffeurOrderView extends JFrame {
    private static final String WINDOW_TITLE = "Active orders";

    // Window dimensions
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private static Object[][] data = new Object[][] {
            {1, "Dummy package 1", 4, LocalDate.now().plusDays(1),"Delivered"},
            {2, "Dummy package 2", 20, LocalDate.now().plusDays(2),"Not delivered"},
            {3, "Dummy package 3", 10, LocalDate.now().plusDays(3),"Not delivered"},
            {4, "Dummy package 4", 50, LocalDate.now().plusDays(4),"Not delivered"},
            {5, "Dummy package 5", 50, LocalDate.now().plusDays(5),"Not delivered"}
    };
    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;
    private JButton viewButton;
    private JButton exitButton;
    private JComboBox statusBox;
    private ComboBoxModel cbModel;

    public ChauffeurOrderView() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        exitButton.addActionListener(e-> {
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
        // Fill table with dummy data
        Object[] columnNames = {"Order ID", "Package", "Portions", "Delivery date","Status"};

        orderTable = new JTable(data, columnNames);

        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }

    private void createComboBox(){
        Object[] status = {"Not delivered","Delivered"};

        statusBox = new JComboBox(status);
        statusBox.setSelectedIndex(0);

    }
    //Sets status as delivered
    private void setStatus(){
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        boolean delivered = choice > 0;
        orderTable.clearSelection();
        //To update database
        OrderFactory.setOrderState(Integer.parseInt(data[selectedRow][0].toString()),delivered);
        data[selectedRow][4] = (delivered)?"Delivered":"Not delivered";
    }
    //Test method
    public static void main(String[] args){
        ChauffeurOrderView coo = new ChauffeurOrderView();
        coo.setVisible(true);
    }
}
