package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * A GUI window which gives you additional info over a selected foodpackage
 */
public class FoodPackageInfoView extends JFrame {
    private JPanel mainPanel;
    private JButton viewPackageButton;
    private JTable dishTable;
    private JButton closeButton;

    private DishTableModel tableModel;
    private FoodPackage foodPackage;


    public FoodPackageInfoView(FoodPackage foodPackage) {
        this.foodPackage = foodPackage;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        viewPackageButton.addActionListener(e -> {
            onView();
        });

        closeButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;
        FoodPackageInfoView dialog = new FoodPackageInfoView(null);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setLocationRelativeTo(null);//Puts window in middle of screen
        System.exit(0);

    }

    /**
     * Called when View button is pressed
     * Opens the DishInfoView GUI
     */
    private void onView() {
        int selectedRow = dishTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        Dish dish = tableModel.getValue(selectedRow);
        final int WIDTH = 700;
        final int HEIGHT = 600;

        DishInfoView dialog = new DishInfoView(dish);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setLocationRelativeTo(null);//Puts window in middle of screen
    }

    /**
     * Called on when close button, escape or cross is pressed
     * Closes the GUI panel
     */
    private void onCancel() {
        dispose();
    }

    private void createTable() {
        ArrayList<Dish> dishList = DishFactory.getDishes(foodPackage.getFoodPackageId());
        Integer[] columns = new Integer[]{DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION}; // Columns can be changed
        tableModel = new DishTableModel(dishList, columns);
        dishTable = new JTable(tableModel);
        dishTable.setFillsViewportHeight(true);
    }

    private void createUIComponents() {
        createTable();
    }
}

