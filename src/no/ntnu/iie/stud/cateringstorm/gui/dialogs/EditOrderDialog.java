package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import jdk.nashorn.internal.scripts.JO;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.SimpleDateFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class EditOrderDialog extends JDialog {
    private JPanel contentPane;
    private JTextField textField1;
    private JComboBox columnBox;
    private JButton saveChangesButton;
    private JButton cancelButton;
    private JDatePickerImpl dateSelect;
    private OrderTableModel orderTableModel;

    private Order order;



    public EditOrderDialog(Order order) {
        this.order = order;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(saveChangesButton);

        saveChangesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
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
        boolean changed = false;
        switch (columnBox.getSelectedIndex()) {
            case 0:
                if (textField1.getText().equals("True") || textField1.getText().equals("true")) {
                    OrderFactory.setOrderPriority(order.getOrderId(), true);
                    changed = true;
                } else if (textField1.getText().equals("False") || textField1.getText().equals("false")) {
                    OrderFactory.setOrderPriority(order.getOrderId(), false);
                    changed = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter either true or false for this field.");
                }
                break;
            case 1:
                String temp = textField1.getText();
                if (temp != null || !temp.isEmpty()) {
                    try {
                        Integer parsedText = Integer.parseInt(temp);
                        OrderFactory.setOrderState(order.getOrderId(), parsedText);
                        changed = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Please only input numbers for this field");
                    }
                    break;
                }
                JOptionPane.showMessageDialog(this, "Please enter a number");
                break;
            case 2:
                System.out.println("Date change");
                Date tempDate = (Date) dateSelect.getModel().getValue();
                Timestamp deliverDate = new Timestamp(tempDate.getTime());
                OrderFactory.setOrderDate(order.getOrderId(), deliverDate);
                changed = true;
                break;
            case 3:
                //TODO Portions
                String portionText = textField1.getText();
                if (portionText != null || !portionText.isEmpty()) {
                    try {
                        Integer parsedText = Integer.parseInt(portionText);
                        OrderFactory.setOrderPortions(order.getOrderId(), parsedText);
                        changed = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Please only input numbers for this field");
                    }
                    break;
                }
                JOptionPane.showMessageDialog(this, "Please enter a number");
                break;

            case 4:

                String desc = textField1.getText();
                OrderFactory.setOrderDescription(order.getOrderId(), desc);
                changed = true;
                break;

            default:
                break;

        }
        if (changed){
            JOptionPane.showMessageDialog(this, "Edit successful");
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createComboBox() {

        ArrayList<Order> list = OrderFactory.getAllOrders();
        Integer[] columns = new Integer[]{OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_STATUS_TEXT, OrderTableModel.COLUMN_DELIVERY_TIME,
                OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_DESCRIPTION};
        orderTableModel = new OrderTableModel(list, columns);
        Object[] objects = new Object[columns.length];

        for (int i = 0; i < columns.length; i++) {
            objects[i] = (new String(orderTableModel.getColumnName(i)));
        }

        columnBox = new JComboBox(objects);
        columnBox.setSelectedIndex(0);
    }

    private void createDatePicker(){
        UtilDateModel model = new UtilDateModel();

        // Dunno
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        dateSelect = new JDatePickerImpl(datePanel, new SimpleDateFormatter());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        createComboBox();
        createDatePicker();

    }

    public static void main(String[] args) {
        EditOrderDialog dialog = new EditOrderDialog(OrderFactory.newOrder(4));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
