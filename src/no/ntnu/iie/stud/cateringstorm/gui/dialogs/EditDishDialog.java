package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.OrderTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditDishDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField editDescription;
    private JTextField editName;
    private JComboBox editType;
    private JComboBox editStatus;
    private JLabel editNameLabel;
    private JLabel editDescriptionLabel;
    private Dish dish;


    public EditDishDialog(Dish dish) {
        this.dish = dish;
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

    private void createComboBoxType(){
        Object[] status = {"Appetizer?","Main?","Desert?"};

        editType = new JComboBox(status);
        editType.setSelectedIndex(0);
    }

    private void createComboBoxActiveStatus(){
        Object[] status = {"Active","Not active"};

        editStatus = new JComboBox(status);
        editStatus.setSelectedIndex(0);
    }

    private void onOK() {

        String name = editName.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a name.");
            return;
        }

        String description = editDescription.getText();
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a description.");
            return;
        }

        int type = editType.getSelectedIndex() + 1;

        boolean active = editStatus.getSelectedIndex()<1;


        Dish dish = null; //= new Dish(placeholder ,name, description, type, active);



        if (dish == null) {
            JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
        } else {
            // Debug code
            JOptionPane.showMessageDialog(this, dish);
        }

        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EditDishDialog dialog = new EditDishDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createComboBoxType();
        createComboBoxActiveStatus();
    }
}
