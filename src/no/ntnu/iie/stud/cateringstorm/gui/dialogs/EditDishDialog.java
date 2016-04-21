package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;


import javax.swing.*;
import java.awt.event.*;

public class EditDishDialog extends JDialog {
    private boolean addedNewValue;

    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField descriptionField;
    private JTextField nameField;
    private JComboBox<String> typeComboBox;
    private JComboBox<String> statusComboBox;
    private JButton swapButton;
    private Dish dish;

    private JTable currentIngredientsTable;

    private JTable availableDishTable;
    private DishTableModel availableDishModel;


    public EditDishDialog(Dish dish) {
        this.dish = dish;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(e -> onOK());

        cancelButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createComboBoxType();
        createComboBoxActiveStatus();
        fillTables();
    }

    private void createComboBoxType(){
        String[] status = {"Appetizer","Main course","Dessert"};

        typeComboBox = new JComboBox<>(status);
        typeComboBox.setSelectedIndex(0);
    }

    private void createComboBoxActiveStatus(){
        String[] status = {"Active","Not active"};

        statusComboBox = new JComboBox<>(status);
        statusComboBox.setSelectedIndex(0);
    }

    private static final Integer[] COLUMNS_AVAILABE_DISHES = { DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION };

    private void fillTables() {
        // Available dishes (all active ones)
        availableDishModel = new DishTableModel(DishFactory.getActiveDishes(), COLUMNS_AVAILABE_DISHES);
        availableDishTable = new JTable(availableDishModel);

        // Current dishes
        currentIngredientsTable = new JTable();
    }

    private void onOK() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if(dialogResult == 0) {

            String name = nameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in a name.");
                return;
            }

            String description = descriptionField.getText();
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in a description.");
                return;
            }

            int type = typeComboBox.getSelectedIndex() + 1;

            boolean active = statusComboBox.getSelectedIndex() < 1;

            dish.setName(name);
            dish.setDescription(description);
            dish.setDishType(type);
            dish.setActive(active);

            DishFactory.updateDish(dish);
            if (DishFactory.updateDish(dish) != 1) {
                JOptionPane.showMessageDialog(this, "Dish was not update, please try again later.");
            }


            if (dish == null) {
                JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
            } else {
                // Debug code
                JOptionPane.showMessageDialog(this, dish);
                addedNewValue = true;
            }

            onCancel();
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public boolean getAddedNewValue() {
        return addedNewValue;
    }

    public static void main(String[] args) {
        EditDishDialog dialog = new EditDishDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
