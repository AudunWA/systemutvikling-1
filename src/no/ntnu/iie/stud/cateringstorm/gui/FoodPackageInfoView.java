package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
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


    public FoodPackageInfoView() {
        add(mainPanel);

        viewPackageButton.addActionListener(e -> {

            int selectedRow = dishTable.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            Dish order = tableModel.getValue(selectedRow);

            //TODO Lag en ViewDishDialog

            //ViewDishDialog dialog = new ViewDishDialog(order);


        });

        dishTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });


    }

    private void createTable(){
        ArrayList<Dish> dishList = DishFactory.getAllDishes();
        Integer[] columns = new Integer[] { DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION }; // Columns can be changed
        tableModel = new DishTableModel(dishList, columns);
        dishTable = new JTable(tableModel);
        dishTable.setFillsViewportHeight(true);
    }

}

