package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.util.ArrayList;

public class ChefMakeOrderDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable dishTable;
    private JScrollPane dishScrollPane;
    private JButton viewIngredientsButton;

    private DishTableModel tableModel;
    private FoodPackageTableModel foodTableModel;
    private Order order;
    private ArrayList<Dish> dishList;

    public ChefMakeOrderDialog(Order order) {
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
        OrderFactory.setOrderState(order.getOrderId(), 0);
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createTable(){

        ArrayList<Integer> packagesId = OrderFactory.getPackages(6);
        dishList = new ArrayList<>();

        for (int i = 0; i < packagesId.size(); i++){
            for (int k = 0; k < DishFactory.getDishes(i).size(); k++){
                dishList.add(DishFactory.getDishes(i).get(k));
            }
        }
        //FIXME add package column???
        Integer[] columns = new Integer[] {DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_TYPE_TEXT, DishTableModel.COLUMN_DESCRIPTION};
        tableModel = new DishTableModel(dishList,columns);
        dishTable = new JTable(tableModel);

        dishTable.getTableHeader().setReorderingAllowed(false);
        dishScrollPane = new JScrollPane(dishTable);
        dishTable.setFillsViewportHeight(true);

    }

    public static void main(String[] args) {
        ChefMakeOrderDialog dialog = new ChefMakeOrderDialog(OrderFactory.getOrder(1));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
    }
}
