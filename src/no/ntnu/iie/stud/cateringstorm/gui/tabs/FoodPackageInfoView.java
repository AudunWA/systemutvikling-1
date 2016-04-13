package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kenan on 07.04.2016.
 */
public class FoodPackageInfoView extends JPanel {
    private JPanel mainPanel;
    private JButton viewPackageButton;
    private JTable dishTable;
    private JPanel buttonPanel;

    private DishTableModel tableModel;
    private FoodPackage foodPackage;


    public FoodPackageInfoView(FoodPackage foodPackage) {
        this.foodPackage = foodPackage;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        viewPackageButton.addActionListener(e -> {

            int selectedRow = dishTable.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            Dish dish = tableModel.getValue(selectedRow);

            //TODO Lag en ViewDishDialog

            //ViewDishDialog dialog = new ViewDishDialog(order);


        });

        dishTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });


    }

    private void createTable(){
        ArrayList<Dish> dishList = DishFactory.getDishes(foodPackage.getFoodPackageId());
        Integer[] columns = new Integer[] { DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION }; // Columns can be changed
        tableModel = new DishTableModel(dishList, columns);
        dishTable = new JTable(tableModel);
        dishTable.setFillsViewportHeight(true);
    }

    public static void main(String[] args){
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new FoodPackageInfoView(FoodPackageFactory.getFoodPackage(1)));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
    }
}

