package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.SimpleDateFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

/**
 * GUI Dialog for editing an existing order in the database
 */

public class EditOrderDialog extends JDialog {
    private JPanel mainPanel;
    private JButton cancelButton;
    private JButton okButton;
    private JButton addRemoveButton;
    private JDatePickerImpl dateSelect;
    private JTable addedTable;
    private JTable packageTable;

    private JTextField customerTextField;
    private JTextField descriptionTextField;

    private JLabel descriptionLabel;
    private JLabel portionsLabel;
    private JLabel priorityLabel;
    private JLabel deliveryDateLabel;
    private JCheckBox priorityBox;
    private JTextField portionsTextField;

    private ArrayList<FoodPackage> addedList;
    private ArrayList<FoodPackage> foodList;

    private SqlDateModel dateModel;

    private Order order;


    public EditOrderDialog(Order order) {
        this.order = order;
        setTitle("Edit order");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        addActionListeners();


        loadData();
    }
    private void addActionListeners(){
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
        addRemoveButton.addActionListener(e -> onAR());
        // call onCancel() when cross is clicked
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
        EditOrderDialog dialog = new EditOrderDialog(null);
        dialog.pack();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Called when ok button is pressed
     * Updates and saves the changes to the existing Order
     */
    private void onOK() {

        String description = descriptionTextField.getText();
        String tempPortion = portionsTextField.getText();
        int portions;
        try {
            portions = Integer.parseInt(tempPortion);
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error, please input numbers in the portions field");
            return;
        }
        if (portions < 1){
            JOptionPane.showMessageDialog(this, "Error, please input a positive portion number");
            return;
        }
        boolean priority = priorityBox.isSelected();
        Timestamp deliveryDate = new Timestamp(dateModel.getValue().getTime());
        if (deliveryDate.before(new java.util.Date(System.currentTimeMillis()))){
            JOptionPane.showMessageDialog(this, "Error, delivery date is set to today or before");
        }

        //OrderFactory.setOrderPriority(order.getOrderId(), priority);
        //OrderFactory.setOrderDescription(order.getOrderId(), description);
        //OrderFactory.setOrderPortions(order.getOrderId(), portions);
        //OrderFactory.setOrderDate(order.getOrderId(), deliveryDate);
        //OrderFactory.setOrderPackages(order.getOrderId(), addedList);

        OrderFactory.updateAllOrderComponents(order.getOrderId(), priority, description, portions, deliveryDate, addedList);

    }

    private void loadData(){

        dateModel.setValue(new Date(order.getDeliveryDate().getTime()));

    }

    private void onAR(){

        if (packageTable.getSelectedRow() > -1 && addedTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. please deselect one by pressing with crtl");
            packageTable.clearSelection();
            addedTable.clearSelection();
        } else {
            if (packageTable.getSelectedRow() > -1) {
                boolean check = true;
                for (FoodPackage packages : addedList) {
                    if (packages.getFoodPackageId() == (packageTable.getSelectedRow() + 1)) {
                        check = false;
                    }
                }
                if (check) {
                    addedList.add(foodList.get(packageTable.getSelectedRow()));
                    ((EntityTableModel) addedTable.getModel()).setRows(addedList);
                    packageTable.clearSelection();
                } else {
                    JOptionPane.showMessageDialog(this, "This order is already added.");
                    packageTable.clearSelection();
                }
            } else if (addedTable.getSelectedRow() > -1 && !packageTable.isColumnSelected(1) && !packageTable.isColumnSelected(2)) {
                addedList.remove(addedTable.getSelectedRow());
                ((EntityTableModel) addedTable.getModel()).setRows(addedList);
                addedTable.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Please unselect the package list. Do this by clicking with crtl down.");
                addedTable.clearSelection();
            }
        }
    }
    /**
     * Called when cancel button, escape or the cross is pressed
     */
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createTextFields(){
        String name = order.getCustomerName();
        customerTextField = new JTextField();
        customerTextField.setText(name);
        customerTextField.setEditable(false);

        String description = order.getDescription();
        descriptionTextField = new JTextField();
        descriptionTextField.setText(description);
        descriptionTextField.setEditable(true);

        String portions = order.getPortions() + "";
        portionsTextField = new JTextField();
        portionsTextField.setText(portions);
        portionsTextField.setEditable(true);
    }

    private void createCheckBox(){
        priorityBox = new JCheckBox("", order.isPriority());
    }

    private void createTables(){

        foodList = FoodPackageFactory.getAllFoodPackages();
        Integer[] columns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        FoodPackageTableModel table = new FoodPackageTableModel(foodList, columns);
        packageTable = new JTable(table);
        packageTable.getTableHeader().setReorderingAllowed(false);

        addedList = FoodPackageFactory.getFoodPackages(order.getOrderId());
        Integer[] addedColumns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        FoodPackageTableModel addedObjects = new FoodPackageTableModel(addedList, columns);
        addedTable = new JTable(addedObjects);
        packageTable.getTableHeader().setReorderingAllowed(false);

    }

    private void createDatePicker() {

        dateModel = new SqlDateModel();

        // Dunno
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        dateSelect = new JDatePickerImpl(datePanel, new SimpleDateFormatter());

    }

    private void createUIComponents() {
        createTables();
        createDatePicker();
        createTextFields();
        createCheckBox();
    }
}
