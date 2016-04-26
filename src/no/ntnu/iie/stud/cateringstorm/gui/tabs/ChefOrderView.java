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

        addActionListeners();
        getNewRenderedTable();
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderTable.setFillsViewportHeight(true);
        updateRecurringOrders();
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

    private void addActionListeners() {
        viewButton.addActionListener(e -> viewOrder());
        statusBox.addActionListener(e -> setStatus());
        refreshButton.addActionListener(e -> refresh());
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
        String[] status = {"Ready for production", "Ready for delivery"};
        statusBox = new JComboBox<>(status);
        statusBox.setSelectedIndex(0);
    }

    private void setStatus() {
        int statusIndex = statusBox.getSelectedIndex();
        int statusType = 0;
        if (statusIndex == 0) {
            statusType = 1;
        } else if (statusIndex == 1) {
            statusType = 0;
        }
        OrderFactory.setOrderState(((Integer) orderTable.getValueAt(orderTable.getSelectedRow(), 0)), statusType);
        tableModel.setRows(OrderFactory.getAllOrdersChef());
    }

    private void viewOrder() {
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
        Order orderModel = OrderFactory.getOrder((Integer) tableModel.getValueAt(orderTable.getSelectedRow(), 0));
        if (orderModel.getStatus() != 0 && orderModel.getStatus() != 3) {
            OrderFactory.setOrderState(orderModel.getOrderId(), 3);
            ChefMakeOrderDialog dialog = new ChefMakeOrderDialog(orderModel);

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
        tableModel.setRows(getChefArray());
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Orders refreshed.").display();
    }

    private void updateRecurringOrders() {
        int newOrders = RecurringOrderFactory.goThroughRecurringOrders();
        tableModel.setRows(getChefArray());
        System.out.println(newOrders + " new orders.");
    }
}
