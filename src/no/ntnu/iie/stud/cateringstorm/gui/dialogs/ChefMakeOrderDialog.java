package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDish;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GUI Dialog for the chef to change the status of orders
 */

public class ChefMakeOrderDialog extends JDialog {
    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTable dishTable;
    private JScrollPane dishScrollPane;
    private JButton viewIngredientsButton;

    private DishTableModel tableModel;
    private FoodPackageTableModel foodTableModel;
    private Order order;
    private ArrayList<Dish> dishList;
    private ArrayList<IngredientDish> ingredientsInOrder;
    private ArrayList<Ingredient> ingredients;

    public ChefMakeOrderDialog(Order order) {
        this.order = order;
        setTitle("Make order");
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        addActionListeners();
    }

    public static void main(String[] args) {
        ChefMakeOrderDialog dialog = new ChefMakeOrderDialog(OrderFactory.getOrder(1));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
    private void addActionListeners(){
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
        viewIngredientsButton.addActionListener(e -> onView());

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    /**
     * Called when view button is pressed
     */
    private void onView() {
        if (dishTable.getSelectedRow() < 0){
            return;
        }
        ChefViewIngredientsDialog view = new ChefViewIngredientsDialog(DishFactory.getDish((Integer) dishTable.getModel().getValueAt(dishTable.getSelectedRow(), 0)));
        final int HEIGHT = 700;
        final int WIDTH = 1000;
        view.pack();
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        view.setSize(WIDTH, HEIGHT);
        view.setLocationRelativeTo(view.getParent());
        view.setVisible(true);

    }

    /**
     * Called when ok button is pressed
     * Changes an orders status from ready for production to in production/ready for delivery
     */
    private void onOK() {
        OrderFactory.setOrderState(order.getOrderId(), 0);
        int viewedOrderId = order.getOrderId();

        ingredientsInOrder = IngredientDishFactory.getAllIngredientsDishesInOrder(viewedOrderId);
        ingredients = IngredientFactory.getAllIngredients();

        for (int i = 0; i < ingredientsInOrder.size(); i++) {
            for (int k = 0; k < ingredients.size(); k++) {
                if (ingredientsInOrder.get(i).getIngredient().getIngredientId() == ingredients.get(k).getIngredientId()) {
                    //System.out.println("BEFORE||||" + "Ingredient: " + ingredients.get(k).getName() + " Amount:" + ingredients.get(k).getAmount());
                    //System.out.println("INGREDIENT_IN_ORDER|||||" + "Ingredient" + ingredientsInOrder.get(i).getIngredient().getName() + " Amount:" + ingredientsInOrder.get(i).getQuantity());
                    double newAmount = IngredientFactory.getIngredient(ingredients.get(k).getIngredientId()).getAmount() - ingredientsInOrder.get(i).getQuantity();
                    System.out.println("Ingredient: " + ingredients.get(k).getName() + " Amount: " + newAmount);
                    IngredientFactory.updateIngredientAmount(ingredients.get(k).getIngredientId(), newAmount);
                }
            }
        }


        dispose();
    }
    /**
     * Called when cancel button, escape or the cross is pressed
     */
    private void onCancel() {
        OrderFactory.setOrderState(order.getOrderId(), 1);
        dispose();
    }

    private void createTable() {

        ArrayList<Integer> packagesId = OrderFactory.getPackagesId(order.getOrderId());
        dishList = new ArrayList<>();

        for (int i = 0; i < packagesId.size(); i++) {
            for (int k = 0; k < DishFactory.getDishes(packagesId.get(i)).size(); k++) {
                dishList.add(DishFactory.getDishes(packagesId.get(i)).get(k));
                System.out.println(DishFactory.getDishes(packagesId.get(i)).get(k));
            }
        }

        Integer[] columns = new Integer[]{DishTableModel.COLUMN_ID, DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_TYPE_TEXT, DishTableModel.COLUMN_DESCRIPTION};
        tableModel = new DishTableModel(dishList, columns);
        dishTable = new JTable(tableModel);

        dishTable.getTableHeader().setReorderingAllowed(false);
        dishScrollPane = new JScrollPane(dishTable);
        dishTable.setFillsViewportHeight(true);

    }

    private void createUIComponents() {
        createTable();
    }
}
