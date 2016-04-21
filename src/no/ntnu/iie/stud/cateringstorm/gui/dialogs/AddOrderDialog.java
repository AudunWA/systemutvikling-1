package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.SimpleDateFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class AddOrderDialog extends JDialog {
    private Employee employee;

    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField forenameText;
    private JLabel descriptionLabel;
    private JLabel portionsLabel;
    private JLabel priorityLabel;
    private JLabel deliveryDateLabel;
    private JTextField descriptionText;
    private JTextField portionsText;
    private JDatePickerImpl dateSelect;
    private JCheckBox priorityBox;
    private JTextField surnameText;
    private JSpinner portionsSlider;
    private JComboBox customerComboBox;
    private JTable packageTable;
    private JTable addedTable;
    private JButton addButton;

    private ArrayList<FoodPackage> addedList;
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<FoodPackage> foodList;

    public AddOrderDialog(Employee employee) {
        this.employee = employee;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onAdd(); }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        customerComboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                customerComboBox.removeAllItems();
                refreshComboBox();
            }
        });

        customerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerComboBox.getSelectedIndex() == CustomerFactory.getAllCustomers().size()){
                    AddCustomerDialog acDialog = new AddCustomerDialog();
                    acDialog.pack();
                    acDialog.setLocationRelativeTo(null);
                    final int WIDTH = 350, HEIGHT = 500;
                    acDialog.setSize(WIDTH,HEIGHT);
                    acDialog.setVisible(true);
                    acDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                }
            }
        });

        packageTable.getSelectionModel().addListSelectionListener(e -> {

        });

        addedTable.getSelectionModel().addListSelectionListener(e -> {

        });

        descriptionText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (descriptionText.getText().equals("Enter description here")) {
                    descriptionText.setText("");
                }
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

    private void onOK() {
// add your code here

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

    private void onAdd(){

        int customerIndex = customerComboBox.getSelectedIndex();
        if (customerIndex == CustomerFactory.getAllCustomers().size()){
            AddCustomerDialog add = new AddCustomerDialog();
            final int WIDTH = 500;
            final int HEIGHT = 400;
            add.pack();
            add.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            add.setSize(WIDTH, HEIGHT);
            add.setLocationRelativeTo(add.getParent());
            add.setVisible(true);
            if(add.hasAddedNewValue()){
                System.out.println("added");
                customerComboBox.addItem(new String(CustomerFactory.getCustomer(CustomerFactory.getAllCustomers().size()).getSurname() + ", " + CustomerFactory.getCustomer(CustomerFactory.getAllCustomers().size()).getForename()));
                customerComboBox.removeItem("New customer");
                customerComboBox.addItem("New customer");
            }
        } else {
            Customer customer = CustomerFactory.getCustomer(customerIndex + 1);
            String customerForename = customer.getForename();
            String customerSurname = customer.getSurname();
            int customerId = CustomerFactory.getIdFromName(customerForename, customerSurname);

            String description = descriptionText.getText();
            if (description.length() > 200){
                JOptionPane.showMessageDialog(this, "The description is too long.");
                return;
            }

            int portions = (int) portionsSlider.getValue();

            if (portions < 1) {
                JOptionPane.showMessageDialog(this, "Please enter a valid portion amount.");
                return;
            }

            boolean priority = priorityBox.isSelected();

            Date temp = (Date) dateSelect.getModel().getValue();
            if (temp == null) {
                JOptionPane.showMessageDialog(this, "Please fill in a delivery date.");
                return;
            } else if (!temp.after(new Date(System.currentTimeMillis()))) {
                JOptionPane.showMessageDialog(this, "Error the delivery date is before current date.");
                return;
            }

            Timestamp deliverDate = new Timestamp(temp.getTime());
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            ArrayList<Integer> test = new ArrayList<>();
            test.add(packageTable.getSelectedRow() + 1);

            orders.add(new Order(0, description, deliverDate, currentTime, portions, priority, employee.getEmployeeId(), CustomerFactory.getCustomer(customerId), 0, 1, 0));

            ArrayList<Integer> packages = new ArrayList<>();
            for (int k = 0; k < addedList.size(); k++) {
                packages.add(addedList.get(k).getFoodPackageId());
            }

            if (packages.size() < 1) {
                JOptionPane.showMessageDialog(this, "Please add package(s)");
                return;
            }

            Order order = OrderFactory.createOrder(description, deliverDate, portions,
                    priority, employee.getEmployeeId(),
                    customerId, 0, packages);

            if (order != null) {
                JOptionPane.showMessageDialog(this, "Add successful");
                addedList = new ArrayList<>();
                ((EntityTableModel) addedTable.getModel()).setRows(addedList);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "An error occurred!\nPlease try again later.", "Error!", JOptionPane.OK_OPTION);
            }
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void refreshComboBox(){
        for (int i = 0; i < CustomerFactory.getAllCustomers().size(); i++) {
            customerComboBox.addItem(new String (CustomerFactory.getCustomer(i+1).getSurname()) + ", " + CustomerFactory.getCustomer(i+1).getForename());
        }
        customerComboBox.addItem("New customer");
    }

    private void createComboBox(){
        customerComboBox = new JComboBox();
        for (int i = 0; i < CustomerFactory.getAllCustomers().size(); i++) {
            customerComboBox.addItem(new String (CustomerFactory.getCustomer(i+1).getSurname()) + ", " + CustomerFactory.getCustomer(i+1).getForename());
        }
    }

    public void createUIComponents() {

        // Create date pickers
        UtilDateModel model = new UtilDateModel();

        // Dunno
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        dateSelect = new JDatePickerImpl(datePanel, new SimpleDateFormatter());

        createComboBox();

        //TODO make the combo box open add new customer when selected
        customerComboBox.addItem("New customer");

        foodList = FoodPackageFactory.getAllFoodPackages();
        Integer[] columns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        FoodPackageTableModel table = new FoodPackageTableModel(foodList, columns);
        packageTable = new JTable(table);
        packageTable.getTableHeader().setReorderingAllowed(false);

        addedList = new ArrayList<>();
        Integer[] addedColumns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        FoodPackageTableModel addedObjects = new FoodPackageTableModel(addedList, columns);
        addedTable = new JTable(addedObjects);
        packageTable.getTableHeader().setReorderingAllowed(false);

    }

    public static void main(String[] args) {
        AddOrderDialog dialog = new AddOrderDialog(EmployeeFactory.getEmployee("drammen"));
        dialog.pack();
        dialog.setVisible(true);
        dialog.setTitle("Order central");
        System.exit(0);
    }
}
