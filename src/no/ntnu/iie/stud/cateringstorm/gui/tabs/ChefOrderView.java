package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDish;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.ChefMakeOrderDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * Orderview for chefs. Chefs are able to edit contents of the order.
 * Created by Audun on 10.03.2016.
 */
public class ChefOrderView extends JPanel {
    private JPanel mainPanel;
    private JTable orderTable;
    private JButton viewButton;
    private JComboBox<String> statusBox;
    private JButton refreshButton;
    private ArrayList<Order> orderList;
    private OrderTableModel tableModel;

    public ChefOrderView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        viewButton.addActionListener(e -> viewOrder());
        statusBox.addActionListener(e -> setStatus());
        refreshButton.addActionListener(e -> refresh());

        getNewRenderedTable();
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderTable.setFillsViewportHeight(true);
        updateRecurringOrders();
    }

    private JTable getNewRenderedTable() {
        orderTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                Order order = tableModel.getValue(row);
                int status = order.getStatus();

                if (status == 1 && !order.isPriority()) {
                    setBackground(new Color(100, 200, 100));
                } else if (status == 0) {
                    setBackground(new Color(150, 150, 150));
                } else if (status == 3 && !order.isPriority()) {
                    setBackground(Color.ORANGE);
                } else if (order.isPriority()) {
                    setBackground(new Color(200, 100, 100));
                    setFont(new Font("BOLD", Font.BOLD, 12));
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
        return orderTable;
    }

    public static void main(String[] args) {
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new ChefOrderView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }

    //Custom initialization of UI components
    private void createUIComponents() {
        createTable();
        createComboBox();
    }

    private ArrayList<Order> getChefArray() {
        orderList = OrderFactory.getAllOrdersChef();
        return orderList;
    }

    private void createTable() {
        getChefArray();

        Integer[] columns = new Integer[]{OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_DESCRIPTION, OrderTableModel.COLUMN_RECURRING_ORDER_ID, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_STATUS_TEXT, OrderTableModel.COLUMN_DELIVERY_TIME};
        tableModel = new OrderTableModel(orderList, columns);
        orderTable = new JTable(tableModel);
    }

    private void createComboBox() {
        String[] status = {"In production", "Ready for delivery"};
        statusBox = new JComboBox<>(status);
        statusBox.setSelectedIndex(0);
    }

    // FIXME: Trouble with wrongly selected indexes. Might be wrong logic i back-end?
    private void setStatus() {
        orderList = OrderFactory.getAllOrders();
        int choice = statusBox.getSelectedIndex();
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn = 5;
        boolean inProduction = choice > 0;
        if (selectedRow > -1) {
            Order order = tableModel.getValue(orderTable.getSelectedRow());
            if (order.getStatus() < 2) {
                orderTable.clearSelection();
                orderTable.getModel().setValueAt((inProduction) ? "Ready for delivery" : "In production", selectedRow, statusColumn);
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Orders status changed.", Toast.Style.SUCCESS).display();
            } else {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "You cannot change this status.", Toast.Style.ERROR).display();
                //JOptionPane.showMessageDialog(this, "Error, chef can't change this status");
            }
        }
    }

    private void viewOrder() {
        // TODO: Implement method opening a new tab DishInfoView, allowing user to view more information of a single
        if (orderTable.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Please select a order");
            return;
        }
        int currentId = (Integer) orderTable.getValueAt(orderTable.getSelectedRow(), 0);

        String JOutput = "Not enough ingredients in storage for this order";
        boolean empty = false;

        ArrayList<IngredientDish> ingredientsInOrder = IngredientDishFactory.getAllIngredientsDishesInOrder(currentId);
        ArrayList<Ingredient> ingredients = IngredientFactory.getAllIngredients();

        for (IngredientDish anIngredientsInOrder : ingredientsInOrder) {
            for (Ingredient ingredient : ingredients) {
                if (anIngredientsInOrder.getIngredient().getIngredientId() == ingredient.getIngredientId()) {
                    if (anIngredientsInOrder.getQuantity() > ingredient.getAmount()) {
                        double newAmount = ingredient.getAmount() - anIngredientsInOrder.getQuantity();
                        empty = true;
                        JOutput += "\n Dish: " + anIngredientsInOrder.getDish().getName() + " Missing ingredient: " + ingredient.getName() + " - Missing amount: " + newAmount;
                    }
                }
            }
        }

        if (empty) {
            JOptionPane.showMessageDialog(this, JOutput);
            return;
        }
        Order order = tableModel.getValue(orderTable.getSelectedRow());
        if (order.getStatus() != 0 && order.getStatus() != 3) {
            OrderFactory.setOrderState(order.getOrderId(), 3);
            ChefMakeOrderDialog dialog = new ChefMakeOrderDialog(order);

            final int HEIGHT = 700;
            final int WIDTH = 1000;
            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setSize(WIDTH, HEIGHT);
            dialog.setLocationRelativeTo(dialog.getParent());

            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error this order is already made!");
        }

        refresh();
    }

    private void refresh() {
        //  TODO: Implement a method updating table for new orders, and removing changed orders from table.
        tableModel.setRows(getChefArray());
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Orders refreshed.").display();
    }

    private void updateRecurringOrders() {
        int newOrders = RecurringOrderFactory.goThroughRecurringOrders();
        tableModel.setRows(getChefArray());
        System.out.println(newOrders + " new orders.");
    }
}
