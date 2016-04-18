package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
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

public class AddSubscriptionDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton addButton;
    private JTable addedTable;
    private JTable selectionTable;
    private JDatePickerImpl fromDate;
    private JDatePickerImpl toDate;
    private JComboBox dayComboBox;
    private JComboBox customerComboBox;
    private JTextField costField;
    private JScrollPane selectionPane;
    private JScrollPane addedPane;

    private double cost;

    private ArrayList<FoodPackage> foodList;
    private ArrayList<FoodPackage> addedList;

    public AddSubscriptionDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onAdd();}
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here

        //TODO add backend logic here

        String day = (String)dayComboBox.getSelectedItem();
        String findCustomer = (String)customerComboBox.getSelectedItem();
        Customer customer;
        for (int i = 0; i < CustomerFactory.getAllCustomers().size(); i++){
            if (findCustomer.equals(new String (CustomerFactory.getCustomer(i+1).getSurname()) + ", " + CustomerFactory.getCustomer(i+1).getForename())){
                customer = CustomerFactory.getCustomer(i);
            }
        }

        Date temp = (Date) fromDate.getModel().getValue();
        Timestamp fromDate = new Timestamp(temp.getTime());

        Date temp2 = (Date) toDate.getModel().getValue();
        Timestamp toDate = new Timestamp(temp2.getTime());

        ArrayList<FoodPackage> packagesSelected = addedList;


        dispose();
    }

    private void onAdd(){

        if (selectionTable.getSelectedRow() > -1 && addedTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. please deselect one by pressing with crtl");
            selectionTable.clearSelection();
            addedTable.clearSelection();
        } else {
            if (selectionTable.getSelectedRow() > -1) {
                boolean check = true;
                for (FoodPackage packages : addedList) {
                    if (packages.getFoodPackageId() == (selectionTable.getSelectedRow() + 1)) {
                        check = false;
                    }
                }
                if (check) {
                    addedList.add(foodList.get(selectionTable.getSelectedRow()));
                    ((EntityTableModel) addedTable.getModel()).setRows(addedList);

                    cost += FoodPackageFactory.getFoodPackageCost(addedList.get(addedList.size()-1).getFoodPackageId());
                    costField.setText("Cost: " + cost);

                    selectionTable.clearSelection();
                } else {
                    JOptionPane.showMessageDialog(this, "This order is already added.");
                    selectionTable.clearSelection();
                }
            } else if (addedTable.getSelectedRow() > -1 && !selectionTable.isColumnSelected(1) && !selectionTable.isColumnSelected(2)) {
                cost -= FoodPackageFactory.getFoodPackageCost(addedList.get(addedTable.getSelectedRow()).getFoodPackageId());
                costField.setText("Cost: " + cost);

                addedList.remove(addedTable.getSelectedRow());

                ((EntityTableModel) addedTable.getModel()).setRows(addedList);
                addedTable.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Please unselect the package list. Do this by clicking with crtl down.");
                addedTable.clearSelection();
            }
        }

    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createUIComponents(){

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
        createComboBoxes();

        costField = new JTextField();
        costField.setText("Cost: ");
        costField.setEditable(false);

    }

    public void createComboBoxes(){

        customerComboBox = new JComboBox();
        for (int i = 0; i < CustomerFactory.getAllCustomers().size(); i++) {
            customerComboBox.addItem(new String (CustomerFactory.getCustomer(i+1).getSurname()) + ", " + CustomerFactory.getCustomer(i+1).getForename());
        }

        dayComboBox = new JComboBox();
        dayComboBox.addItem("Monday");
        dayComboBox.addItem("Tuesday");
        dayComboBox.addItem("Wednesday");
        dayComboBox.addItem("Thursday");
        dayComboBox.addItem("Friday");
        dayComboBox.addItem("Saturday");
        dayComboBox.addItem("Sunday");


    }

    public void createPackageSelectionTable(){

        foodList = FoodPackageFactory.getAllFoodPackages();
        Integer[] columns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        FoodPackageTableModel table = new FoodPackageTableModel(foodList, columns);
        selectionTable = new JTable(table);
        selectionTable.getTableHeader().setReorderingAllowed(false);

        addedList = new ArrayList<>();
        Integer[] addedColumns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST};
        FoodPackageTableModel addedObjects = new FoodPackageTableModel(addedList, columns);
        addedTable = new JTable(addedObjects);
        addedTable.getTableHeader().setReorderingAllowed(false);

    }

    public static void main(String[] args) {
        AddSubscriptionDialog dialog = new AddSubscriptionDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
