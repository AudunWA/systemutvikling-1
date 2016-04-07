package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
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

    private boolean addedNewValue;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
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
    private JComboBox customerList;

    public AddOrderDialog(Employee employee) {
        this.employee = employee;
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

        int customerIndex = customerList.getSelectedIndex();
        String customerForename = CustomerFactory.viewSingleCustomer(customerIndex).getForename();
        String customerSurname = CustomerFactory.viewSingleCustomer(customerIndex).getSurname();
        int customerId = CustomerFactory.getIdFromCustomerName(customerForename, customerSurname);

        String description = descriptionText.getText();

        int portions = (int)portionsSlider.getValue();

        if (portions < 1) {
            JOptionPane.showMessageDialog(this, "Please enter a valid portion amount");
            return;
        }

        boolean priority = priorityBox.isSelected();

        Date temp = (Date)dateSelect.getModel().getValue();
        Timestamp deliverDate = new Timestamp(temp.getTime());
        if (deliverDate == null){
            JOptionPane.showMessageDialog(this, "Please fill in a delivery date.");
            return;
        } else if (!deliverDate.after(new Date(System.currentTimeMillis()))){
            JOptionPane.showMessageDialog(this, "Error the delivery date is before current date.");
            return;
        }


        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        ArrayList<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(2);

        Order order = OrderFactory.createOrder(description, deliverDate, portions, priority, employee.getEmployeeId(), customerId, 0, test);
        if (order == null){
            JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
        } else {
            addedNewValue = true;
        }
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
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

        customerList = new JComboBox();
        for (int i = 0; i < CustomerFactory.getAllCustomers().size(); i++) {
            customerList.addItem(new String (CustomerFactory.viewSingleCustomer(i+1).getSurname()) + ", " + CustomerFactory.viewSingleCustomer(i+1).getForename());
        }
    }

    public static void main(String[] args) {
        AddOrderDialog dialog = new AddOrderDialog(EmployeeFactory.newEmployee("drammen"));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


}
