package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDish;
import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDishFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientDishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GUI Dialog for adding a dish to the database
 */

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

    private JButton okButton;

    private JScrollPane addedIngredientPane;
    private JScrollPane selectionIngredientPane;
    private JTable addedIngredientTable;
    private JTable selectionIngredientTable;
    private JButton addOrRemoveButton;
    private JSpinner addRemoveSpinner;

    private ArrayList<Ingredient> selectionList;
    private ArrayList<IngredientDish> addedList;

    private IngredientDishTableModel addedTableModel;


    public AddDishDialog() {
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(addButton);

        addOrRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAR();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });
        addedIngredientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                return;
            }
            selectionIngredientTable.clearSelection();
        });
        selectionIngredientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                return;
            }
            addedIngredientTable.clearSelection();
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

    public static void main(String[] args) {
        final int WIDTH = 700;
        final int HEIGHT = 600;
        AddDishDialog dialog = new AddDishDialog();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setVisible(true);
        System.exit(0);

    }

    private void createComboBoxType() {
        Object[] status = {"Appetizer", "Main course", "Dessert"};

        dishType = new JComboBox(status);
        dishType.setSelectedIndex(0);
    }

    private void createComboBoxActiveStatus() {
        Object[] status = {"Active", "Not active"};

        activeStatus = new JComboBox(status);
        activeStatus.setSelectedIndex(0);
    }

    /**
     * Called when Ok button is pressed
     * Creates a new dish with the user inputted attributes
     */
    private void onOk() {

        String name;
        String description;
        int type;
        boolean active;

        name = dishName.getText();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(this, "Dish name must be filled in");
            return;
        }
        description = dishDescription.getText();
        type = dishType.getSelectedIndex() + 1;
        if ((activeStatus.getSelectedItem()).equals("Active")) {
            active = false;
        } else {
            active = true;
        }

        String Joutput = "The following ingredients have been added to the dish: " + name + "\n";

        for (IngredientDish ingredientDishes : IngredientDishFactory.createDish(addedList, name, description, type, active)) {
            Joutput += "Ingredient: " + ingredientDishes.getIngredient().getName() + " " + ingredientDishes.getQuantity() + ingredientDishes.getUnit() + ".\n";
        }

        JOptionPane.showMessageDialog(this, Joutput);

    }

    /**
     * Called when cancel button, escape or the cross is pressed
     */
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    /**
     * Called when add/remove betton is pressed
     */
    public void onAR() {

        if ((Integer) addRemoveSpinner.getValue() < 1) {
            JOptionPane.showMessageDialog(this, "Please set a positive amount on the spinner");
            return;
        }

        boolean check = true;

        if (addedIngredientTable.getSelectedRow() > -1) {
            if (addedList.get(addedIngredientTable.getSelectedRow()).getQuantity() < (Integer) addRemoveSpinner.getValue() + 1) {
                addedList.remove(addedIngredientTable.getSelectedRow());
            } else {
                addedList.get(addedIngredientTable.getSelectedRow()).setQuantity(addedList.get(addedIngredientTable.getSelectedRow()).getQuantity() - (Integer) addRemoveSpinner.getValue());
            }
        } else if (selectionIngredientTable.getSelectedRow() > -1) {
            //addedList.add()
            IngredientDish ingDish = new IngredientDish(IngredientFactory.getIngredient(selectionList.get(selectionIngredientTable.getSelectedRow()).getIngredientId()), null, (Integer) addRemoveSpinner.getValue(), selectionList.get(selectionIngredientTable.getSelectedRow()).getUnit());

            for (int i = 0; i < addedList.size(); i++) {
                if (addedList.get(i).getIngredient().getIngredientId() == ingDish.getIngredient().getIngredientId()) {
                    addedList.get(i).setQuantity(addedList.get(i).getQuantity() + ingDish.getQuantity());
                    check = false;
                }
            }

            if (check) {
                addedList.add(ingDish);
            }
        }
        addedTableModel.setRows(addedList);

    }

    public void createTables() {

        selectionList = IngredientFactory.getAllIngredients();
        Integer[] columns = new Integer[]{IngredientTableModel.COLUMN_ID, IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_UNIT};
        IngredientTableModel selectionTableModel = new IngredientTableModel(selectionList, columns);
        selectionIngredientTable = new JTable(selectionTableModel);
        selectionIngredientTable.getTableHeader().setReorderingAllowed(false);

        addedList = new ArrayList<>();
        Integer[] columnsForAdded = new Integer[]{IngredientDishTableModel.COLUMN_INGREDIENT_ID, IngredientDishTableModel.COLUMN_INGREDIENT_NAME, IngredientDishTableModel.COLUMN_QUANTITY, IngredientDishTableModel.COLUMN_UNIT};
        addedTableModel = new IngredientDishTableModel(addedList, columnsForAdded);
        addedIngredientTable = new JTable(addedTableModel);
        addedIngredientTable.getTableHeader().setReorderingAllowed(false);

    }

    private void createUIComponents() {
        createComboBoxType();
        createComboBoxActiveStatus();
        createTables();
    }
}
