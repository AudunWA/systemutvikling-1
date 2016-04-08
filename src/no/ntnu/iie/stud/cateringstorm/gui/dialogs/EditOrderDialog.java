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
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private JTable table2;
    private JScrollPane originalOrderPane;
    private JScrollPane table1Pane;
    private Order order;

    private ArrayList<Order> orderList;


    public EditOrderDialog(Order order) {
        this.order = order;
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
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void setTables(){
        orderList = new ArrayList<>();
        orderList.add(order);
        Integer[] columns = new Integer[]{OrderTableModel.COLUMN_ID, OrderTableModel.COLUMN_DESCRIPTION, OrderTableModel.COLUMN_DELIVERY_TIME, OrderTableModel.COLUMN_ORDER_TIME, OrderTableModel.COLUMN_PORTIONS, OrderTableModel.COLUMN_PRIORITY, OrderTableModel.COLUMN_CUSTOMER_ID, OrderTableModel.COLUMN_STATUS_TEXT};
        OrderTableModel addedObjects = new OrderTableModel(orderList, columns);
        table1 = new JTable(addedObjects);
        table1.getTableHeader().setReorderingAllowed(false);

        table2 = new JTable(addedObjects);
        table2.getTableHeader().setReorderingAllowed(false);

        originalOrderPane = new JScrollPane(table1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        originalOrderPane.setPreferredSize(new Dimension(100,500));
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        setTables();

    }

    public static void main(String[] args) {
        EditOrderDialog dialog = new EditOrderDialog(OrderFactory.getAllOrders().get(2));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
