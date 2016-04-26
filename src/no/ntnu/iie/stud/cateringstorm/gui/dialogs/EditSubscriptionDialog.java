package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrderFactory;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.Subscription;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.SubscriptionFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.RecurringOrderTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.DateUtil;
import no.ntnu.iie.stud.cateringstorm.gui.util.SimpleDateFormatter;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Properties;

/**
 * GUI Dialog for adding a subscription to the database
 */

public class EditSubscriptionDialog extends JDialog {
    //private static final Integer[] COLUMNS_AVAILABLE_DISHES = {SubscriptionTableModel.COLUMN, SubscriptionTableModel.COLUMN_DESCRIPTION}

    private static final int MIN_HOURS_BETWEEN_ORDERS = 1;
    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JButton addRemoveButton;
    private JComboBox<String> dayComboBox;
    private JComboBox<Customer> customerComboBox;
    private JTextField costField;
    private JSpinner amountSpinner;
    private JSpinner timeSpinner;
    private JTable rightSideTable;
    private FoodPackageTableModel rightSideModel;
    private JTable leftSideTable;
    private RecurringOrderTableModel leftSideModel;
    private double cost;
    private Subscription subscription;
    private int customerIndex; // Used on load time

    private JDatePickerImpl fromDatePicker;
    private SqlDateModel fromDateModel;

    private JDatePickerImpl toDatePicker;
    private SqlDateModel toDateModel;

    public EditSubscriptionDialog(Subscription subscription) {
        this.subscription = subscription;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        fillComboBoxes();
        initializeTimeSpinner();

        // Add event listeners
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
        addRemoveButton.addActionListener(e -> onAR());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        leftSideTable.getSelectionModel().addListSelectionListener(e -> {
            int index = leftSideTable.getSelectedRow();
            if (!e.getValueIsAdjusting() || index == -1) {
                return;
            }
            // Unselect other table
            rightSideTable.clearSelection();
            RecurringOrder order = leftSideModel.getValue(index);

            dayComboBox.setSelectedIndex(order.getWeekday());
            amountSpinner.setValue(order.getAmount());
            timeSpinner.setValue(new Time(Timestamp.valueOf(DateUtil.convertRelativeTime(order.getRelativeTime())).getTime()));
        });

        rightSideTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                return;
            }
            // Unselect other table
            leftSideTable.clearSelection();
        });

        dayComboBox.addActionListener(e -> {
            int index = leftSideTable.getSelectedRow();
            int dayIndex = dayComboBox.getSelectedIndex();

            if (index == -1 || dayIndex == -1) {
                return;
            }

            RecurringOrder order = leftSideModel.getValue(index);
            order.setWeekday(dayIndex);
            leftSideModel.updateRow(index);
        });

        // Set minimum amount on spinner (could be 1, but we want to display our error toast)
        SpinnerNumberModel amountSpinnerModel = (SpinnerNumberModel) amountSpinner.getModel(); // Default model
        amountSpinnerModel.setMinimum(0);

        amountSpinner.addChangeListener(e -> {
            int index = leftSideTable.getSelectedRow();
            int newAmount = (int) amountSpinner.getValue();

            if (index == -1) {
                return;
            }

            if (newAmount < 1) {
                Toast.makeText(this, "Amount too low.", Toast.Style.ERROR).display();
                return;
            }

            RecurringOrder order = leftSideModel.getValue(index);
            order.setAmount(newAmount);
            leftSideModel.updateRow(index);
        });

        timeSpinner.addChangeListener(e -> {
            int index = leftSideTable.getSelectedRow();
            int newTime = getTimeSpinnerValue();

            if (index == -1) {
                return;
            }

            if (newTime < 0) {
                Toast.makeText(this, "Invalid time.", Toast.Style.ERROR).display();
                return;
            }

            RecurringOrder order = leftSideModel.getValue(index);
            order.setRelativeTime(newTime);
            leftSideModel.updateRow(index);
        });
        loadData();
    }

    public static void main(String[] args) {
        EditSubscriptionDialog dialog = new EditSubscriptionDialog(SubscriptionFactory.getSubscription(2));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Fills up the components with data from the food package.
     * Table data is not loaded here, but in createTables().
     */
    private void loadData() {
        // Set date picker model values
        fromDateModel.setValue(subscription.getStartDate());
        toDateModel.setValue(subscription.getEndDate());

        // Everything else
        cost = subscription.getCost();
        costField.setText(subscription.getCost() + "");
        customerComboBox.setSelectedIndex(customerIndex);
    }

    /**
     * Initializes and fills the tables with appropriate data
     */
    private void createTables() {
        Integer[] columns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        Integer[] addedColumns = new Integer[]{RecurringOrderTableModel.COLUMN_FOOD_PACKAGE_NAME, RecurringOrderTableModel.COLUMN_AMOUNT, RecurringOrderTableModel.COLUMN_FOOD_PACKAGE_COST, RecurringOrderTableModel.COLUMN_WEEKDAY, RecurringOrderTableModel.COLUMN_RELATIVE_TIME};

        // Available dishes (all active ones)
        rightSideModel = new FoodPackageTableModel(FoodPackageFactory.getAllFoodPackages(), columns);
        rightSideTable = new JTable(rightSideModel);
        rightSideTable.getTableHeader().setReorderingAllowed(false);

        // Current dishes
        leftSideModel = new RecurringOrderTableModel(RecurringOrderFactory.getRecurringOrders(subscription.getSubscriptionId()), addedColumns);
        leftSideTable = new JTable(leftSideModel);
        leftSideTable.getTableHeader().setReorderingAllowed(false);
    }

    /*~
     * Called when OK button is pressed.
     * Creates a new Subscription with attributes from user input
     */
    private void onOK() {
        Customer customer = (Customer) customerComboBox.getSelectedItem();
        Date startDate = ((SqlDateModel) fromDatePicker.getModel()).getValue();
        Date endDate = ((SqlDateModel) toDatePicker.getModel()).getValue();

        subscription.setCustomerId(customer.getCustomerId());
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setCost(cost);

        if (!SubscriptionFactory.updateSubscription(subscription, leftSideModel.getRowsClone())) {
            JOptionPane.showMessageDialog(this, "Dish was not updated, please try again later.");
        }
        dispose();
    }

    /**
     * Called when add/remove button (arrow between tables) is pressed
     */
    private void onAR() {
        // Check if both tables are selected
        if (rightSideTable.getSelectedRow() > -1 && leftSideTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. Error.");
            rightSideTable.clearSelection();
            leftSideTable.clearSelection();
            return;
        }

        if (rightSideTable.getSelectedRow() > -1) {
            FoodPackage selectedFoodPackage = rightSideModel.getValue(rightSideTable.getSelectedRow());
            RecurringOrder existingRecurringOrder = null;
            int existingRecurringOrderIndex = -1;
            ArrayList<RecurringOrder> recurringOrders = leftSideModel.getRowsClone();
            for (int i = 0; i < recurringOrders.size(); i++) {
                RecurringOrder recurringOrder = recurringOrders.get(i);
                if (recurringOrder.getFoodPackageId() == selectedFoodPackage.getFoodPackageId()) {
                    existingRecurringOrder = recurringOrder;
                    existingRecurringOrderIndex = i;
                    break;
                }
            }
            if (existingRecurringOrder == null) {
                // Create a new recurring order and add it
                int relativeTime = DateUtil.convertToRelativeTime(DateUtil.convertDate((java.util.Date) timeSpinner.getModel().getValue()).toLocalTime());
                RecurringOrder newOrder = new RecurringOrder(-1, dayComboBox.getSelectedIndex(), relativeTime,1, null, selectedFoodPackage);
                leftSideModel.addRow(newOrder);

            } else {
                // Increment existing with one
                existingRecurringOrder.incrementAmount();
                leftSideModel.updateRow(existingRecurringOrderIndex);
            }
            cost += selectedFoodPackage.getCost();
            costField.setText(cost + "");
        } else if (leftSideTable.getSelectedRow() > -1) {
            RecurringOrder selectedRecurringOrder = leftSideModel.getValue(leftSideTable.getSelectedRow());

            if (selectedRecurringOrder.getAmount() > 0) {
                // Decrement
                selectedRecurringOrder.decrementAmount();
                leftSideModel.updateRow(leftSideTable.getSelectedRow());
                cost -= selectedRecurringOrder.getFoodPackageCost();
                costField.setText(cost + "");
            }
//            else {
//                // Remove
//                leftSideModel.removeRow(leftSideTable.getSelectedRow());
//                leftSideTable.clearSelection();
//            }
        } else {
            Toast.makeText(this, "Select a row.", Toast.Style.ERROR).display();
        }
    }

    /**
     * Called when cancel button, escape or the cross is pressed
     */
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // Create date pickers
        fromDateModel = new SqlDateModel();
        toDateModel = new SqlDateModel();

        // Dunno
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl fromPanel = new JDatePanelImpl(fromDateModel, p);
        JDatePanelImpl toPanel = new JDatePanelImpl(toDateModel, p);
        fromDatePicker = new JDatePickerImpl(fromPanel, new SimpleDateFormatter());
        toDatePicker = new JDatePickerImpl(toPanel, new SimpleDateFormatter());

        costField = new JTextField();
        costField.setEditable(false);

        createTables();
    }

    private void initializeTimeSpinner() {
        SpinnerModel model = new SpinnerDateModel();

        timeSpinner.setModel(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(editor);
    }

    public void fillComboBoxes() {
        // Fill customers
        ArrayList<Customer> customers = CustomerFactory.getAllCustomers();
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            customerComboBox.addItem(customer);

            // Set customer index if this is the selected customer.
            if(subscription.getCustomerId() == customer.getCustomerId()) {
                customerIndex = i;
            }
        }

        // Lazy lambda, handles correct name rendering
        customerComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> new JLabel(value.getSurname() + ", " + value.getForename()));

        // Fill weekdays
        for (String day : DateUtil.WEEKDAYS) {
            dayComboBox.addItem(day);
        }
    }

    private int getTimeSpinnerValue() {
        return DateUtil.convertToRelativeTime(DateUtil.convertDate((java.util.Date) timeSpinner.getModel().getValue()).toLocalTime());
    }

    /**
     * Compares a new recurring order time with all the existing ones.
     *
     * @param newWeekday      The weekday of the new order
     * @param newRelativeTime The seconds after midnight of the new order
     * @return If any order is set to be delivered within MIN_HOURS_BETWEEN_ORDERS hours of the new one, return false.
     */
    private boolean isValidAdd(int newWeekday, int newRelativeTime) {
        for (RecurringOrder recurringOrder : leftSideModel.getRowsClone()) {
            if (recurringOrder.getWeekday() == newWeekday) {
                LocalDateTime recurringOrderLocalTime = DateUtil.convertRelativeTime(recurringOrder.getRelativeTime());
                LocalDateTime newRelativeLocalTime = DateUtil.convertRelativeTime(newRelativeTime);
                if (ChronoUnit.HOURS.between(recurringOrderLocalTime, newRelativeLocalTime) < MIN_HOURS_BETWEEN_ORDERS) {
                    return false;
                }
            }
        }
        return true;
    }

    public Subscription getSubscription() {
        return subscription;
    }
}
