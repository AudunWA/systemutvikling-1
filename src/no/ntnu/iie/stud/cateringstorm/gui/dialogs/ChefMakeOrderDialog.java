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

        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        viewIngredientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onView();
            }
        });

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

    public static void main(String[] args) {
        ChefMakeOrderDialog dialog = new ChefMakeOrderDialog(OrderFactory.getOrder(1));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onView() {
        ChefViewIngredientsDialog view = new ChefViewIngredientsDialog(DishFactory.getDish((Integer) dishTable.getModel().getValueAt(dishTable.getSelectedRow(), 0)));
        final int HEIGHT = 700;
        final int WIDTH = 1000;
        view.pack();
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        view.setSize(WIDTH, HEIGHT);
        view.setLocationRelativeTo(view.getParent());
        view.setVisible(true);

    }

    private void onOK() {
// add your code here
        OrderFactory.setOrderState(order.getOrderId(), 0);
        int viewedOrderId = order.getOrderId();

        ingredientsInOrder = IngredientDishFactory.getAllIngredientsDishesInOrder(viewedOrderId);
        ingredients = IngredientFactory.getAllIngredients();

        /*for(int i = 0; i < ingredientsInOrder.size(); i++) {
            int id = ingredientsInOrder.get(i).getIngredientId();
            double usedAmount = ingredientsInOrder.get(i).getAmount();
            ingredients = IngredientFactory.getAllIngredients();
            double storageAmount = ingredients.get(i).getAmount();
            double newAmount = storageAmount - usedAmount;
            IngredientFactory.updateIngredientAmount(id, newAmount);
        }*/
        //FIXME THE INGREDIENTS GET THE AMOUNT IN STORAGE INSTEAD OF QUANTITY!!!!!!!!!!
        for (int i = 0; i < ingredientsInOrder.size(); i++) {
            for (int k = 0; k < ingredients.size(); k++) {
                if (ingredientsInOrder.get(i).getIngredient().getIngredientId() == ingredients.get(k).getIngredientId()) {
                    //System.out.println("BEFORE||||" + "Ingredient: " + ingredients.get(k).getName() + " Amount:" + ingredients.get(k).getAmount());
                    //System.out.println("INGREDIENT_IN_ORDER|||||" + "Ingredient" + ingredientsInOrder.get(i).getIngredient().getName() + " Amount:" + ingredientsInOrder.get(i).getQuantity());
                    double newAmount = ingredients.get(k).getAmount() - ingredientsInOrder.get(i).getQuantity();
                    //System.out.println("Ingredient: " + ingredients.get(k).getName() + " Amount: " + newAmount);
                    IngredientFactory.updateIngredientAmount(ingredients.get(k).getIngredientId(), newAmount);
                }
            }
        }


        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        OrderFactory.setOrderState(order.getOrderId(), 1);
        dispose();
    }

    private void createTable() {

        ArrayList<Integer> packagesId = OrderFactory.getPackages(6);
        dishList = new ArrayList<>();

        for (int i = 0; i < packagesId.size(); i++) {
            for (int k = 0; k < DishFactory.getDishes(i).size(); k++) {
                dishList.add(DishFactory.getDishes(i).get(k));
            }
        }
        //FIXME add package column???
        Integer[] columns = new Integer[]{DishTableModel.COLUMN_ID, DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_TYPE_TEXT, DishTableModel.COLUMN_DESCRIPTION};
        tableModel = new DishTableModel(dishList, columns);
        dishTable = new JTable(tableModel);

        dishTable.getTableHeader().setReorderingAllowed(false);
        dishScrollPane = new JScrollPane(dishTable);
        dishTable.setFillsViewportHeight(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
    }
}
