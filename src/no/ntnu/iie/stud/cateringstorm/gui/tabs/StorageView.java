package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddIngredientDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * GUI for displaying the inventory of ingredients to chefs and nutrition experts(?).
 * Lets the user add, edit and remove ingredients.
 */
public class StorageView extends JPanel {
    private JPanel mainPanel;
    private JTable ingredientTable;
    private JButton incrementSupplyButton;

    private JButton addIngredientButton;
    private JTextField searchField;
    private JButton refreshButton;
    private JTextField incrementValueField;
    private IngredientTableModel tableModel;

    private ArrayList<Ingredient> ingredientList;

    public StorageView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        addDocumentListener();
        addActionListeners();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StorageView");
        frame.setContentPane(new StorageView().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Used to create a custom table renderer.
     *
     * @param table The table which should have the renderer.
     * @return The same table.
     */
    private static JTable getNewRenderedTable(final JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {


                if (((Date) table.getValueAt(row, 3)).before(new Date(System.currentTimeMillis() + 86400000 * 2))) {
                    setBackground(new Color(200, 100, 100));
                } else if (((Date) table.getValueAt(row, 3)).before(new Date(System.currentTimeMillis() + 86400000 * 10))) {
                    setBackground(Color.ORANGE);
                } else {
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                }

                return this;
            }
        });
        return table;
    }

    private void addActionListeners() {
        addIngredientButton.addActionListener(e -> onIngredientAddClick());
        incrementSupplyButton.addActionListener(e -> onIncrementClick());
        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchField.setText("");
            }
        });

        refreshButton.addActionListener(e -> {
            refresh();
        });
    }

    private void addDocumentListener() {


        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDocument();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDocument();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchDocument();
            }

            public void searchDocument() {

                ArrayList<Ingredient> copy = new ArrayList<>();

                for (Ingredient anIngredientList : ingredientList) {
                    if ((anIngredientList.getName().toLowerCase().contains(searchField.getText().toLowerCase()) || (anIngredientList.getName()).toLowerCase().contains(searchField.getText().toLowerCase()))) {
                        copy.add(anIngredientList);

                    }
                }
                tableModel.setRows(copy);

            }
        });
    }

    /*private void addIngredient(){
        AddIngredientDialog dialog = new AddIngredientDialog();
        dialog.pack();
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        if (dialog.getAddedNewValue()) {
            //FIXME toast gives nullpointer
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Ingredient added.", Toast.LENGTH_SHORT, Toast.Style.SUCCESS).display();

            ingredientList = IngredientFactory.getAllIngredients();

            // Refresh data
            tableModel.setRows(IngredientFactory.getAllIngredients());
        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Ingredient add failed.", Toast.LENGTH_SHORT, Toast.Style.ERROR).display();
        }
    }*/
   /* private void incrementSupply(){
        int selectedRow = ingredientTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        Ingredient ingredient = tableModel.getValue(selectedRow);
        ingredient.incrementAmount();
        int affectedRows = IngredientFactory.updateIngredientAmount(ingredient.getIngredientId(), ingredient.getAmount());

        if (affectedRows == 1) {
            tableModel.setRow(selectedRow, ingredient);
        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Supply was not incremented", Toast.Style.ERROR).display();
        }
    }*/
    private void refresh() {
        ingredientList = IngredientFactory.getAllIngredients();
        tableModel.setRows(ingredientList);
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Storage refreshed.").display();
    }

    /**
     * Called when increment button has been pressed.
     * Increments supply of selected ingredient by value defined by a text field.
     */
    private void onIncrementClick() {
        int selectedRow = ingredientTable.getSelectedRow();
        if (selectedRow == -1) {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "No ingredient selected.").display();
            return;
        }

        double incrementValue;
        try {
            incrementValue = Double.parseDouble(incrementValueField.getText().replace(',', '.'));
        } catch (NumberFormatException e) {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Invalid incrementation.", Toast.Style.ERROR).display();
            return;
        }

        Ingredient ingredient = tableModel.getValue(selectedRow);
        ingredient.setAmount(ingredient.getAmount() + incrementValue);
        int affectedRows = IngredientFactory.updateIngredientAmount(ingredient.getIngredientId(), ingredient.getAmount());

        if (affectedRows == 1) {
            tableModel.setRow(selectedRow, ingredient);
        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Incrementation failed.", Toast.Style.ERROR).display();
        }
    }

    /**
     * Called when the add ingredient button has been pressed.
     * Displays an AddIngredientDialog.
     */
    private void onIngredientAddClick() {
        AddIngredientDialog dialog = new AddIngredientDialog();
        dialog.pack();
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        if (dialog.getAddedNewValue()) {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Ingredient added.", Toast.LENGTH_SHORT, Toast.Style.SUCCESS).display();
            ingredientList = IngredientFactory.getAllIngredients();

            // Refresh data
            tableModel.setRows(IngredientFactory.getAllIngredients());
        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Ingredient add failed.", Toast.LENGTH_SHORT, Toast.Style.ERROR).display();
        }
    }

    private void createUIComponents() {
        createTable();
    }

    private void createTable() {
        ingredientList = IngredientFactory.getAllIngredients();
        Integer[] columns = new Integer[]{IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_DESCRIPTION, IngredientTableModel.COLUMN_ID, IngredientTableModel.COLUMN_EXPIRE_DATE, IngredientTableModel.COLUMN_AMOUNT, IngredientTableModel.COLUMN_UNIT};

        tableModel = new IngredientTableModel(ingredientList, columns);
        ingredientTable = new JTable(tableModel);
        getNewRenderedTable(ingredientTable);
        ingredientTable.getTableHeader().setReorderingAllowed(false);
    }
}
