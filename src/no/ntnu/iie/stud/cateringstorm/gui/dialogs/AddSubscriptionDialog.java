package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.RecurringOrderTableModel;
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

public class AddSubscriptionDialog extends JDialog {
    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JButton addButton;
    private JTable selectedPackagesTable;
    private JTable availablePackagesTable;
    private JDatePickerImpl fromDate;
    private JDatePickerImpl toDate;
    private JComboBox<String> dayComboBox;
    private JComboBox<Customer> customerComboBox;
    private JTextField costField;
    private JSpinner amountSpinner;
    private JSpinner timeSpinner;

    private RecurringOrderTableModel selectedPackagesModel;

    private double cost;

    private ArrayList<FoodPackage> foodList;
    private ArrayList<RecurringOrder> addedList;

    public AddSubscriptionDialog() {
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        fillComboBoxes();
        initializeTimeSpinner();

        // Add event listeners
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
        addButton.addActionListener(e -> onAdd());

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

        selectedPackagesTable.getSelectionModel().addListSelectionListener(e -> {
            int index = selectedPackagesTable.getSelectedRow();
            if (!e.getValueIsAdjusting() || index == -1) {
                return;
            }
            // Unselect other table
            availablePackagesTable.clearSelection();
            RecurringOrder order = selectedPackagesModel.getValue(index);

            dayComboBox.setSelectedIndex(order.getWeekday());
            amountSpinner.setValue(order.getAmount());
            timeSpinner.setValue(new Time(Timestamp.valueOf(DateUtil.convertRelativeTime(order.getRelativeTime())).getTime()));
        });

        availablePackagesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                return;
            }
            // Unselect other table
            selectedPackagesTable.clearSelection();
        });

        dayComboBox.addActionListener(e -> {
            int index = selectedPackagesTable.getSelectedRow();
            int dayIndex = dayComboBox.getSelectedIndex();

            if(index == -1 || dayIndex == -1) {
                return;
            }

            RecurringOrder order = selectedPackagesModel.getValue(index);
            order.setWeekday(dayIndex);
            selectedPackagesModel.updateRow(index);
        });

        // Set minimum amount on spinner (could be 1, but we want to display our error toast)
        SpinnerNumberModel amountSpinnerModel = (SpinnerNumberModel)amountSpinner.getModel(); // Default model
        amountSpinnerModel.setMinimum(0);

        amountSpinner.addChangeListener(e -> {
            int index = selectedPackagesTable.getSelectedRow();
            int newAmount = (int)amountSpinner.getValue();

            if(index == -1) {
                return;
            }

            if(newAmount < 1) {
                Toast.makeText(this, "Amount too low.", Toast.Style.ERROR).display();
                return;
            }

            RecurringOrder order = selectedPackagesModel.getValue(index);
            order.setAmount(newAmount);
            selectedPackagesModel.updateRow(index);
        });

        timeSpinner.addChangeListener(e -> {
            int index = selectedPackagesTable.getSelectedRow();
            int newTime = getTimeSpinnerValue();

            if(index == -1) {
                return;
            }

            if(newTime < 0) {
                Toast.makeText(this, "Invalid time.", Toast.Style.ERROR).display();
                return;
            }

            RecurringOrder order = selectedPackagesModel.getValue(index);
            order.setRelativeTime(newTime);
            selectedPackagesModel.updateRow(index);
        });
    }

    /**
     * Called when OK button is pressed.
     */
    private void onOK() {
        String day = (String) dayComboBox.getSelectedItem();
        Customer customer = (Customer) customerComboBox.getSelectedItem();

        Date temp = (Date) fromDate.getModel().getValue();
        Timestamp fromDate = new Timestamp(temp.getTime());

        Date temp2 = (Date) toDate.getModel().getValue();
        Timestamp toDate = new Timestamp(temp2.getTime());

        //TODO add query here (remember to add a try-catch)

        dispose();
    }

    /**
     * Called when add button (arrow between tables) is pressed
     */
    private void onAdd() {
        // Check if both tables are selected
        if (availablePackagesTable.getSelectedRow() > -1 && selectedPackagesTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. Error.");
            availablePackagesTable.clearSelection();
            selectedPackagesTable.clearSelection();
            return;
        }

        if (availablePackagesTable.getSelectedRow() > -1) {
            FoodPackage selectedFoodPackage = foodList.get(availablePackagesTable.getSelectedRow());
            RecurringOrder existingRecurringOrder = null;
            int existingRecurringOrderIndex = -1;
            for (int i = 0; i < addedList.size(); i++) {
                RecurringOrder recurringOrder = addedList.get(i);
                if (recurringOrder.getFoodPackageId() == selectedFoodPackage.getFoodPackageId()) {
                    existingRecurringOrder = recurringOrder;
                    existingRecurringOrderIndex = i;
                    break;
                }
            }
            if (existingRecurringOrder == null) {
                // Create a new recurring order and add it
                int relativeTime = DateUtil.convertToRelativeTime(DateUtil.convertDate((Date) timeSpinner.getModel().getValue()).toLocalTime());
                RecurringOrder newOrder = new RecurringOrder(-1, -1, selectedFoodPackage.getFoodPackageId(), dayComboBox.getSelectedIndex(), relativeTime, 1, selectedFoodPackage);
                addedList.add(newOrder);
                selectedPackagesModel.setRows(addedList);

            } else {
                // Increment existing with one
                existingRecurringOrder.incrementAmount();
                selectedPackagesModel.updateRow(existingRecurringOrderIndex);
            }
            cost += FoodPackageFactory.getFoodPackageCost(addedList.get(addedList.size() - 1).getFoodPackageId());
            costField.setText("Cost: " + cost);
        } else if (selectedPackagesTable.getSelectedRow() > -1) {
            cost -= FoodPackageFactory.getFoodPackageCost(addedList.get(selectedPackagesTable.getSelectedRow()).getFoodPackageId());
            costField.setText("Cost: " + cost);

            RecurringOrder recurringOrder = selectedPackagesModel.getValue(selectedPackagesTable.getSelectedRow());

            if(recurringOrder.getAmount() > 1) {
                // Decrement
                recurringOrder.decrementAmount();
                selectedPackagesModel.updateRow(selectedPackagesTable.getSelectedRow());
            } else {
                addedList.remove(selectedPackagesTable.getSelectedRow());
                selectedPackagesModel.setRows(addedList);
                selectedPackagesTable.clearSelection();
            }
        } else {
            Toast.makeText(this, "Select a row.", Toast.Style.ERROR).display();
        }
    }

    /**
     * Called when Cancel button is pressed
     */
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

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

    private void initializeTimeSpinner(){
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

    private int getTimeSpinnerValue(){
        return DateUtil.convertToRelativeTime(DateUtil.convertDate((Date)timeSpinner.getModel().getValue()).toLocalTime());
    }

    public void createPackageSelectionTable() {
        foodList = FoodPackageFactory.getAllFoodPackages();
        Integer[] columns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        FoodPackageTableModel table = new FoodPackageTableModel(foodList, columns);
        availablePackagesTable = new JTable(table);
        availablePackagesTable.getTableHeader().setReorderingAllowed(false);

        addedList = new ArrayList<>();
        Integer[] addedColumns = new Integer[]{RecurringOrderTableModel.COLUMN_FOOD_PACKAGE_NAME, RecurringOrderTableModel.COLUMN_AMOUNT, RecurringOrderTableModel.COLUMN_FOOD_PACKAGE_COST, RecurringOrderTableModel.COLUMN_WEEKDAY, RecurringOrderTableModel.COLUMN_RELATIVE_TIME};
        selectedPackagesModel = new RecurringOrderTableModel(addedList, addedColumns);
        selectedPackagesTable = new JTable(selectedPackagesModel);
        selectedPackagesTable.getTableHeader().setReorderingAllowed(false);

    }

    private static final int MIN_HOURS_BETWEEN_ORDERS = 1;

    /**
     * Compares a new recurring order time with all the existing ones.
     *
     * @param newWeekday      The weekday of the new order
     * @param newRelativeTime The seconds after midnight of the new order
     * @return If any order is set to be delivered within MIN_HOURS_BETWEEN_ORDERS hours of the new one, return false.
     */
    private boolean isValidAdd(int newWeekday, int newRelativeTime) {
        for (RecurringOrder recurringOrder : addedList) {
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

    public static void main(String[] args) {
        AddSubscriptionDialog dialog = new AddSubscriptionDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
