package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditOrderDialog extends JDialog {
    private JPanel contentPane;
    private JTextField textField1;
    private JComboBox columnBox;
    private JButton saveChangesButton;
    private JButton cancelButton;
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
        dispose();
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

    private void createUIComponents() {
        // TODO: place custom component creation code here

        createComboBox();

    }

    public static void main(String[] args) {
        EditOrderDialog dialog = new EditOrderDialog(OrderFactory.getAllOrders().get(2));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
