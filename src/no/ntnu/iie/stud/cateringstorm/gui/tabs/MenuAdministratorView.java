package no.ntnu.iie.stud.cateringstorm.gui.tabs;

/*import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;*/
import javax.swing.*;

import java.util.ArrayList;


import javax.swing.*;

/**
 * Created by kenan on 01.04.2016.
 */
public class MenuAdministratorView extends JPanel {
    private static final String WINDOW_TITLE = "Menu Administrator";

    // Window dimensions
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private JButton addDishButton;
    private JButton editDishButton;
    private JButton removeDishButton;
    //Use DishTableModel
    private JTable dishTable;
    private JPanel mainPanel;
    private JButton exitButton;
    private JScrollPane dishPane;
    //private DishTableModel tableModel;
    private String[] columnNames = new String[6];
    private static ArrayList<Object> dishList = new ArrayList<Object>();

    public MenuAdministratorView() {
        add(mainPanel);
        exitButton.addActionListener(e-> {
            //Change window to dishinfo
        });
        editDishButton.addActionListener(e -> {
            //setDish();
        });

        dishTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();
    }
    private void createTable(){
       // dishList = DishFactory.getAllDishes();

        //tableModel = new DishTableModel(dishList,columnNames);
        //dishTable = new JTable(tableModel);
        //dishPane = new JScrollPane(dishTable);
        //dishTable.setFillsViewportHeight(true);
    }

    public static void main(String[] args){
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new MenuAdministratorView());
        frame.setVisible(true);
        frame.setTitle(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
    }
}

