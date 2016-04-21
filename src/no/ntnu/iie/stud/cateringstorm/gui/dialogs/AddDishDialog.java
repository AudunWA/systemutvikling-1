package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddDishDialog extends JDialog {
    private JPanel mainPanel;
    private JButton addButton;
    private JButton cancelButton;
    private JTextField dishName;
    private JTextField dishDescription;
    private JComboBox activeStatus;
    private JComboBox dishType;
    private JLabel dishNameLabel;
    private JLabel dishDescriptionLabel;
    private JTable addedTable;
    private JButton okButton;
    private JTable ingredientTable;

    private ArrayList<Ingredient> addedList;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Dish> dishes = new ArrayList<>();



    public AddDishDialog() {
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onOk(); }
        });
        addedTable.getSelectionModel().addListSelectionListener(e -> {

        });
        ingredientTable.getSelectionModel().addListSelectionListener(e -> {

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
    private void createComboBoxType(){
        Object[] status = {"Appetizer","Main course","Dessert"};

        dishType = new JComboBox(status);
        dishType.setSelectedIndex(0);
    }

    private void createComboBoxActiveStatus(){
        Object[] status = {"Active","Not active"};

        activeStatus = new JComboBox(status);
        activeStatus.setSelectedIndex(0);
    }

    /*
    private void onAdd() {
        String name = dishName.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a name.");
            return;
        }

        String description = dishDescription.getText();
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a description.");
            return;
        }

        int type = dishType.getSelectedIndex() + 1;

        boolean active = activeStatus.getSelectedIndex()<1;

        ArrayList<Integer> test = new ArrayList<>();
        test.add(ingredientTable.getSelectedRow() + 1);

        dishes.add(new Dish(0, name, description, type, active));

        int check = DishFactory.getAllDishes().size();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int k = 0; k < addedList.size(); k++) {
            ingredients.add(addedList.get(k));
        }

        if (ingredients.size() < 1) {
            JOptionPane.showMessageDialog(this, "Please add package(s)");
            return;
        }

        Dish dish = DishFactory.createDish(name, description, type, active, ingredients);

        if (DishFactory.getAllDishes().size() > check) {
            JOptionPane.showMessageDialog(this, "Add successful");
            addedList = new ArrayList<>();
            ((EntityTableModel) addedTable.getModel()).setRows(addedList);
        }
        JOptionPane.showMessageDialog(this, dish);
    }
    */

    private void onOk() {
        if (ingredientTable.getSelectedRow() > -1 && addedTable.getSelectedRow() > -1) {
            JOptionPane.showMessageDialog(this, "Both tables selected. please deselect one by pressing with crtl");
            ingredientTable.clearSelection();
            addedTable.clearSelection();
        } else {
            if (ingredientTable.getSelectedRow() > -1) {
                boolean check = true;
                for (Ingredient ingredients : addedList) {
                    if (ingredients.getIngredientId() == (ingredientTable.getSelectedRow() + 1)) {
                        check = false;
                    }
                }
                if (check) {
                    addedList.add(ingredientList.get(ingredientTable.getSelectedRow()));
                    ((EntityTableModel) addedTable.getModel()).setRows(addedList);
                    ingredientTable.clearSelection();
                } else {
                    JOptionPane.showMessageDialog(this, "This ingredient is already added.");
                    ingredientTable.clearSelection();
                }
            } else if (addedTable.getSelectedRow() > -1 && !ingredientTable.isColumnSelected(1) && !ingredientTable.isColumnSelected(2)) {
                addedList.remove(addedTable.getSelectedRow());
                ((EntityTableModel) addedTable.getModel()).setRows(addedList);
                addedTable.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Please unselect the ingredient list. Do this by clicking with ctrl down.");
                addedTable.clearSelection();
            }
        }

    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        final int WIDTH = 700;
        final int HEIGHT = 600;
        AddDishDialog dialog = new AddDishDialog();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setVisible(true);
        System.exit(0);

    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createComboBoxType();
        createComboBoxActiveStatus();
    }
}
