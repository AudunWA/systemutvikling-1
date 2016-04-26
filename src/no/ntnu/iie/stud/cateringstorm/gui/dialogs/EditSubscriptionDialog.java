package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.Subscription;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.SubscriptionFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.RecurringOrderTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.SubscriptionTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.DateUtil;
import no.ntnu.iie.stud.cateringstorm.gui.util.SimpleDateFormatter;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
    private JDatePickerImpl fromDate;
    private JDatePickerImpl toDate;
    private JComboBox<String> dayComboBox;
    private JComboBox<Customer> customerComboBox;
    private JTextField costField;
    private JSpinner amountSpinner;
    private JSpinner timeSpinner;
    private JTable rightSideTable;
    private FoodPackageTableModel availabelpackagesModel;
    private JTable leftSideTable;
    private RecurringOrderTableModel selectedPackagesModel;
    private double cost;
    private ArrayList<FoodPackage> foodList; // TODO: Replace with table model methods
    private Subscription subscription;

    public EditSubscriptionDialog(Subscription subscription) {

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
        mainPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        leftSideTable.getSelectionModel().addListSelectionListener(e -> {
            int index = leftSideTable.getSelectedRow();
            if (!e.getValueIsAdjusting() || index == -1) {
                return;
            }
            // Unselect other table
            rightSideTable.clearSelection();
            RecurringOrder order = selectedPackagesModel.getValue(index);

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

            RecurringOrder order = selectedPackagesModel.getValue(index);
            order.setWeekday(dayIndex);
            selectedPackagesModel.updateRow(index);
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

            RecurringOrder order = selectedPackagesModel.getValue(index);
            order.setAmount(newAmount);
            selectedPackagesModel.updateRow(index);
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

            RecurringOrder order = selectedPackagesModel.getValue(index);
            order.setRelativeTime(newTime);
            selectedPackagesModel.updateRow(index);
        });
    }

    public static void main(String[] args) {
        AddSubscriptionDialog dialog = new AddSubscriptionDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
    /*
    private void loadData() {
        customerComboBox.setSelectedIndex(subscription.getCustomerId());
        costField.setText(subscription.getCost());
        activeCheckbox.setSelected();
    }
    */

    /*~
     * Called when OK button is pressed.
     * Creates a new Subscription with attributes from user input
     */

    private void onOK() {
        Customer customer = (Customer) customerComboBox.getSelectedItem();
        java.sql.Date startDate = new java.sql.Date(((Date) fromDate.getModel().getValue()).getTime());
        java.sql.Date endDate = new java.sql.Date(((Date) toDate.getModel().getValue()).getTime());

        if (!SubscriptionFactory.(subscription, leftSideModel.getRowsClone())) {
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
            FoodPackage selectedFoodPackage = foodList.get(rightSideTable.getSelectedRow());
            RecurringOrder existingRecurringOrder = null;
            int existingRecurringOrderIndex = -1;
            ArrayList<RecurringOrder> recurringOrders = selectedPackagesModel.getRowsClone();
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
                int relativeTime = DateUtil.convertToRelativeTime(DateUtil.convertDate((Date) timeSpinner.getModel().getValue()).toLocalTime());
                RecurringOrder newOrder = new RecurringOrder(-1, dayComboBox.getSelectedIndex(), relativeTime,1, null, selectedFoodPackage);
                selectedPackagesModel.addRow(newOrder);

            } else {
                // Increment existing with one
                existingRecurringOrder.incrementAmount();
                selectedPackagesModel.updateRow(existingRecurringOrderIndex);
            }
            cost += selectedFoodPackage.getCost();
            costField.setText("Cost: " + cost);
        } else if (leftSideTable.getSelectedRow() > -1) {
            RecurringOrder selectedRecurringOrder = selectedPackagesModel.getValue(leftSideTable.getSelectedRow());

            cost -= selectedRecurringOrder.getFoodPackageCost();
            costField.setText("Cost: " + cost);

            if (selectedRecurringOrder.getAmount() > 1) {
                // Decrement
                selectedRecurringOrder.decrementAmount();
                selectedPackagesModel.updateRow(leftSideTable.getSelectedRow());
            } else {
                // Remove
                selectedPackagesModel.removeRow(leftSideTable.getSelectedRow());
                leftSideTable.clearSelection();
            }
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
    /*
    private void createTables() {
        // Available dishes (all active ones)
        rightTableModel = new SubscriptionTableModel(FoodPackageFactory.getActiveFoodPackages(), COLUMNS_AVAILABLE_DISHES);
        rightSideTable = new JTable(rightTableModel);

        // Current dishes
        leftSideModel = new SubscriptionTableModel(SubscriptionFactory.getSubscription(subscription.getSubscriptionId()), COLUMNS_AVAILABLE_DISHES);
        leftSideTable = new JTable(leftSideModel);
    }
    */

    private void createUIComponents() {
        // Create date pickers
        UtilDateModel model1 = new UtilDateModel();
        UtilDateModel model2 = new UtilDateModel();

        // Dunno
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl pane1 = new JDatePanelImpl(model1, p);
        JDatePanelImpl pane2 = new JDatePanelImpl(model2, p);
        fromDate = new JDatePickerImpl(pane1, new SimpleDateFormatter());
        toDate = new JDatePickerImpl(pane2, new SimpleDateFormatter());

        createPackageSelectionTable();

        costField = new JTextField();
        costField.setText("Cost: ");
        costField.setEditable(false);

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
        for (Customer customer : customers) {
            customerComboBox.addItem(customer);
        }

        // Lazy lambda, handles correct name rendering
        customerComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> new JLabel(value.getSurname() + ", " + value.getForename()));

        // Fill weekdays
        for (String day : DateUtil.WEEKDAYS) {
            dayComboBox.addItem(day);
        }
    }

    private int getTimeSpinnerValue() {
        return DateUtil.convertToRelativeTime(DateUtil.convertDate((Date) timeSpinner.getModel().getValue()).toLocalTime());
    }

    public void createPackageSelectionTable() {
        foodList = FoodPackageFactory.getAllFoodPackages();
        Integer[] columns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        availabelpackagesModel = new FoodPackageTableModel(foodList, columns);
        rightSideTable = new JTable(availabelpackagesModel);
        rightSideTable.getTableHeader().setReorderingAllowed(false);

        Integer[] addedColumns = new Integer[]{RecurringOrderTableModel.COLUMN_FOOD_PACKAGE_NAME, RecurringOrderTableModel.COLUMN_AMOUNT, RecurringOrderTableModel.COLUMN_FOOD_PACKAGE_COST, RecurringOrderTableModel.COLUMN_WEEKDAY, RecurringOrderTableModel.COLUMN_RELATIVE_TIME};
        selectedPackagesModel = new RecurringOrderTableModel(new ArrayList<>(), addedColumns);
        leftSideTable = new JTable(selectedPackagesModel);
        leftSideTable.getTableHeader().setReorderingAllowed(false);
    }

    /**
     * Compares a new recurring order time with all the existing ones.
     *
     * @param newWeekday      The weekday of the new order
     * @param newRelativeTime The seconds after midnight of the new order
     * @return If any order is set to be delivered within MIN_HOURS_BETWEEN_ORDERS hours of the new one, return false.
     */
    private boolean isValidAdd(int newWeekday, int newRelativeTime) {
        for (RecurringOrder recurringOrder : selectedPackagesModel.getRowsClone()) {
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
