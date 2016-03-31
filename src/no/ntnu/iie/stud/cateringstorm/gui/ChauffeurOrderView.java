package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;

import javax.swing.*;
import java.time.LocalDate;

/**
 * Represents GUI with overview of orders for the chauffeur
 * Created by Audun on 10.03.2016.
 */
public class ChauffeurOrderView extends JPanel {
    private static final String WINDOW_TITLE = "Active orders";

    // Window dimensions
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;
    private static Object[][] data = new Object[][] {
            {1, "Nils Nilsen", 4, LocalDate.now().plusDays(1),"Kongens gate 2","Delivered"},
            {2, "Kenan Mahic", 20, LocalDate.now().plusDays(2),"Aksel Nilsen veg 8","Not delivered"},
            {3, "Kat", 10, LocalDate.now().plusDays(3),"Leirfossvegen 47","Not delivered"},
            {4, "Awa 500", 50, LocalDate.now().plusDays(4),"Ratesvingen 8","Not delivered"},
            {5, "Håvard", 50, LocalDate.now().plusDays(5),"Kroppanmarka 10","Not delivered"}
    };
    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JTable orderTable;

    private JButton exitButton;
    private JComboBox statusBox;
    private ComboBoxModel cbModel;

    public ChauffeurOrderView() {
        super(false);
        //setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setContentPane(mainPanel);
        add(mainPanel);
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
        Object[] columnNames = {"Order ID", "Customer", "Portions", "Delivery date","Location","Status"};

        orderTable = new JTable(data, columnNames);

        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }

    private void createComboBox(){
        Object[] status = {"Not delivered","Delivered"};

        statusBox = new JComboBox(status);
        statusBox.setSelectedIndex(0);

    }
    //Sets order status as delivered/not delivered
    private void setStatus(){
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        boolean delivered = choice > 0;
        int arrLength = data[0].length-1;
        orderTable.clearSelection();
        //To update database
        OrderFactory.setOrderState(Integer.parseInt(data[selectedRow][0].toString()),delivered);
        data[selectedRow][arrLength] = (delivered)?"Delivered":"Not delivered";
    }
    //Test method
    public static void main(String[] args){
        ChauffeurOrderView coo = new ChauffeurOrderView();
        coo.setVisible(true);
    }
}
