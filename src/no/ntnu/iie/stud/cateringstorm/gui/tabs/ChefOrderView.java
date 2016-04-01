package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.gui.tabs.ChauffeurOrderView;

import javax.swing.*;
import java.time.LocalDate;

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
    private JButton setStateButton;
    private ComboBoxModel cbModel;

    public ChefOrderView() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setStateButton.addActionListener(e-> {
            //Change delivered status to an order by importing data from combobox
        });
        viewButton.addActionListener(e -> {
            //TODO: Implement method opening a new tab, allowing user to view more information of a single order
        });
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();

    }
    private void createTable(){

    }

    public static void main(String[] args){
        ChefOrderView overView = new ChefOrderView();
        overView.setVisible(true);
    }
}
